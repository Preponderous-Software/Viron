// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Supplies the pooled {@link DataSource} that
 * {@link preponderous.viron.database.DbInteractions} draws connections from, together with the
 * transaction manager that gives the multi-statement write paths their boundaries.
 *
 * <p>The pool is built from {@link DbConfig} rather than Spring Boot's
 * {@code spring.datasource.*} properties, so the existing {@code database.*} property
 * names keep working. Declaring the bean here also makes Spring Boot's
 * {@code DataSourceAutoConfiguration} back off, so exactly one pool exists.
 *
 * <p>The pool is deliberately constructed with setters rather than
 * {@code new HikariDataSource(HikariConfig)}: the setter form defers pool start-up to the
 * first {@code getConnection()} call, so the application context still starts when the
 * database is unreachable. That matches how the service behaved when it opened a single
 * {@code DriverManager} connection, and keeps context-only tests from needing a database.
 */
@Configuration
public class DataSourceConfig {

    /** Name reported by Hikari in logs and JMX, to distinguish this pool in shared deployments. */
    private static final String POOL_NAME = "viron-pool";

    @Bean(destroyMethod = "close")
    public DataSource dataSource(DbConfig dbConfig) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName(POOL_NAME);
        dataSource.setJdbcUrl(dbConfig.getDbUrl());
        dataSource.setUsername(dbConfig.getDbUsername());
        dataSource.setPassword(dbConfig.getDbPassword());
        return dataSource;
    }

    /**
     * Manages the transactions that {@code @Transactional} write paths run in.
     *
     * <p>This is the manager {@code DataSourceUtils} cooperates with: it binds one pooled
     * connection to the current thread with auto-commit off for the length of the transaction,
     * so every {@link preponderous.viron.database.DbInteractions} call made inside the boundary
     * joins it and a thrown runtime exception discards the whole sequence rather than leaving
     * a half-applied cascade behind.
     *
     * <p>Spring Boot would auto-configure an equivalent manager, but only while exactly one
     * {@link DataSource} candidate exists and no manager is declared. Declaring it next to the
     * pool it wraps keeps the boundaries from depending on that condition continuing to hold.
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
