// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the pooled {@link DataSource} introduced for #194: that it is built from
 * {@link DbConfig}, that it does not contact the database until first use, and that it is the
 * only {@link DataSource} in the context (so Spring Boot's auto-configured one has backed off).
 */
@SpringBootTest
class DataSourceConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void dataSourceIsAPoolBuiltFromDbConfig() {
        DbConfig config = new DbConfig();
        config.setDbUrl("jdbc:h2:mem:viron_datasourceconfig");
        config.setDbUsername("sa");
        config.setDbPassword("secret");

        HikariDataSource dataSource = (HikariDataSource) new DataSourceConfig().dataSource(config);
        try {
            assertThat(dataSource.getJdbcUrl()).isEqualTo("jdbc:h2:mem:viron_datasourceconfig");
            assertThat(dataSource.getUsername()).isEqualTo("sa");
            assertThat(dataSource.getPassword()).isEqualTo("secret");
            assertThat(dataSource.getPoolName()).isEqualTo("viron-pool");
        } finally {
            dataSource.close();
        }
    }

    // Pool start-up is deferred so the context still starts when the database is unreachable.
    @Test
    void poolIsNotStartedUntilFirstConnectionIsRequested() {
        DbConfig config = new DbConfig();
        config.setDbUrl("jdbc:h2:mem:viron_datasourceconfig_lazy");
        config.setDbUsername("sa");
        config.setDbPassword("");

        HikariDataSource dataSource = (HikariDataSource) new DataSourceConfig().dataSource(config);
        try {
            assertThat(dataSource.getHikariPoolMXBean()).isNull();
        } finally {
            dataSource.close();
        }
    }

    @Test
    void contextExposesExactlyOnePooledDataSource() {
        assertThat(applicationContext.getBeanNamesForType(DataSource.class)).hasSize(1);
        assertThat(applicationContext.getBean(DataSource.class)).isInstanceOf(HikariDataSource.class);
    }
}
