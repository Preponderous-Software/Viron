// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.trace;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Starts the real application context with usage reporting pointed at a loopback stub, and
 * checks that exactly one {@code startup} event for {@code viron} arrives once the context is
 * ready. The stub is the JDK's own HTTP server, so nothing outside this JVM is contacted.
 */
@SpringBootTest(properties = {
        "usage-reporting.enabled=true",
        "usage-reporting.key=test-key",
        "usage-reporting.version=test-version"
})
class UsageReporterStartupEventTest {

    private record Received(String method, String path, String authorization, String body) {}

    private static HttpServer stub;
    private static final BlockingQueue<Received> received = new LinkedBlockingQueue<>();

    @BeforeAll
    static void startStub() throws Exception {
        stub = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        stub.createContext("/", exchange -> {
            byte[] body = readAll(exchange.getRequestBody());
            received.add(new Received(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    exchange.getRequestHeaders().getFirst("Authorization"),
                    new String(body, StandardCharsets.UTF_8)));
            exchange.sendResponseHeaders(201, -1);
            exchange.close();
        });
        stub.start();
    }

    @AfterAll
    static void stopStub() {
        stub.stop(0);
    }

    @DynamicPropertySource
    static void pointReportingAtTheStub(DynamicPropertyRegistry registry) {
        registry.add("usage-reporting.endpoint", () -> "http://127.0.0.1:" + stub.getAddress().getPort());
    }

    @Autowired
    private UsageReporter reporter;

    @Test
    void oneStartupEventReachesTheEndpointOnceTheContextIsReady() throws Exception {
        assertThat(reporter.isReporting()).isTrue();

        Received event = received.poll(10, TimeUnit.SECONDS);
        assertThat(event).as("startup event delivered to the stub").isNotNull();
        assertThat(event.method()).isEqualTo("POST");
        assertThat(event.path()).isEqualTo("/api/metrics");
        assertThat(event.authorization()).isEqualTo("Bearer test-key");
        assertThat(event.body())
                .contains("\"application\":\"viron\"")
                .contains("\"name\":\"startup\"")
                .contains("\"service\":\"true\"")
                .contains("\"version\":\"test-version\"");
        assertThat(event.body()).doesNotContain("value");

        // Only the one event: nothing per request, nothing repeated.
        assertThat(received.poll(500, TimeUnit.MILLISECONDS)).isNull();
    }

    private static byte[] readAll(InputStream in) throws java.io.IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
}
