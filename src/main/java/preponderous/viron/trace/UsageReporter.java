// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.trace;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import preponderous.viron.VironApplication;

/**
 * Reports a single {@code startup} event to the trace usage-tracking service once the
 * application is ready, and nothing else.
 *
 * <p>What is sent: the program name ({@value #APPLICATION}), the event name, the service's
 * own version (the Maven project version, or the jar's {@code Implementation-Version} when the
 * property is absent) and the static tag {@code service=true}, which lets the operator page
 * hide hosted services from the fleet view. Nothing per request, and nothing about users,
 * hosts, addresses or data is ever included.
 *
 * <p>The event is sent by {@link TraceClient}, which returns immediately, never throws, and
 * queues at most {@link TraceClient#QUEUE_CAPACITY} reports, so an unreachable trace server
 * costs the service nothing but a dropped report. Reporting is on by default and is
 * configured through the {@code usage-reporting.*} properties in {@code application.properties}
 * (each with an environment override): set {@code usage-reporting.enabled=false} (or the
 * {@code USAGE_REPORTING_ENABLED=false} environment variable) to turn it off. A blank key
 * also disables it, and a malformed endpoint yields a disabled client rather than a failed
 * start-up.
 */
@Component
@Slf4j
public class UsageReporter {

    /** The name the program key was issued for; the {@code application} field of every event. */
    static final String APPLICATION = "viron";

    static final String STARTUP_EVENT = "startup";

    private final TraceClient client;
    private final String version;

    public UsageReporter(
            @Value("${usage-reporting.enabled:true}") boolean enabled,
            @Value("${usage-reporting.endpoint:https://trace.danielstephenson.dev}") String endpoint,
            @Value("${usage-reporting.key:}") String key,
            @Value("${usage-reporting.version:}") String version) {
        this.client = buildClient(enabled, endpoint, key);
        this.version = resolveVersion(version);
        if (client.isEnabled()) {
            log.info("Usage reporting is on: a startup event (program name and version only) is sent to {}; "
                    + "set usage-reporting.enabled=false (USAGE_REPORTING_ENABLED=false) to turn it off.", endpoint);
        } else {
            log.info("Usage reporting is off.");
        }
    }

    /** Whether a startup event will actually be sent. */
    public boolean isReporting() {
        return client.isEnabled();
    }

    /** Sends the one startup event, once the context is fully up. Returns immediately. */
    @EventListener(ApplicationReadyEvent.class)
    public void reportStartup() {
        client.report(STARTUP_EVENT, null, startupTags());
    }

    /** Stops the client's sending thread when the context shuts down. */
    @PreDestroy
    public void close() {
        client.close();
    }

    /** The tags attached to the startup event: {@code service=true}, plus {@code version} when known. */
    Map<String, String> startupTags() {
        Map<String, String> tags = new LinkedHashMap<>();
        tags.put("service", "true");
        if (version != null) {
            tags.put("version", version);
        }
        return tags;
    }

    /** The version that will be reported, or {@code null} if none could be determined. */
    String version() {
        return version;
    }

    private static TraceClient buildClient(boolean enabled, String endpoint, String key) {
        if (!enabled) {
            return TraceClient.disabled();
        }
        try {
            return TraceClient.builder(endpoint, APPLICATION)
                    .key(key)
                    .enabled(true)
                    .logger(Logger.getLogger(UsageReporter.class.getName()))
                    .build();
        } catch (RuntimeException invalid) {
            // A usage report must never be the reason the service fails to start.
            log.warn("Usage reporting is off: the configured endpoint could not be used ({})", invalid.getMessage());
            return TraceClient.disabled();
        }
    }

    private static String resolveVersion(String configured) {
        if (configured != null && !configured.trim().isEmpty() && !configured.contains("@project.version@")) {
            return configured.trim();
        }
        Package pkg = VironApplication.class.getPackage();
        String fromManifest = pkg == null ? null : pkg.getImplementationVersion();
        return fromManifest == null || fromManifest.trim().isEmpty() ? null : fromManifest.trim();
    }
}
