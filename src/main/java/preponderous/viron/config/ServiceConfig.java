package preponderous.viron.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties("config")
@PropertySource("classpath:application.properties")
public class ServiceConfig {
    private String vironHost;
    private int vironPort;

    public String getVironHost() {
        return vironHost;
    }

    public void setVironHost(String vironHost) {
        this.vironHost = vironHost;
    }

    public int getVironPort() {
        return vironPort;
    }

    public void setVironPort(int vironPort) {
        this.vironPort = vironPort;
    }
}
