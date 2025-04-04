package preponderous.viron.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@ConfigurationProperties("config")
@PropertySource("classpath:application.properties")
public class ServiceConfig {
    private String vironHost;
    private int vironPort;
}
