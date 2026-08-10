// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.database;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import preponderous.viron.config.DataSourceConfig;
import preponderous.viron.config.DbConfig;
import preponderous.viron.exceptions.ServiceException;
import preponderous.viron.repositories.EntityRepositoryImpl;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Proves the transaction boundaries added for #194 actually roll back, by running the real
 * {@link EntityRepositoryImpl#deleteById(int)} — two dependent statements — against an in-memory
 * H2 database through the production {@link DataSourceConfig} wiring.
 *
 * <p>The schema is a minimal stand-in for the Postgres one: the entity table, the placement table
 * whose row has to be cleared first, and a guard table that exists only so the second statement
 * can be made to fail on demand.
 */
class TransactionRollbackTest {

    private static final int ENTITY_ID = 1;
    private static final int LOCATION_ID = 7;

    private DataSource dataSource;
    private DbInteractions dbInteractions;
    private EntityRepositoryImpl entityRepository;
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        dataSource = new DataSourceConfig().dataSource(h2Config());
        dbInteractions = new DbInteractions(dataSource);
        entityRepository = new EntityRepositoryImpl(dbInteractions);

        PlatformTransactionManager transactionManager = new DataSourceConfig().transactionManager(dataSource);
        transactionTemplate = new TransactionTemplate(transactionManager);

        dbInteractions.update("DROP TABLE IF EXISTS viron.entity_delete_guard");
        dbInteractions.update("DROP TABLE IF EXISTS viron.entity_location");
        dbInteractions.update("DROP TABLE IF EXISTS viron.entity");
        dbInteractions.update("CREATE SCHEMA IF NOT EXISTS viron");
        dbInteractions.update(
                "CREATE TABLE viron.entity (entity_id INT PRIMARY KEY, name VARCHAR(255), creation_date VARCHAR(255))");
        dbInteractions.update(
                "CREATE TABLE viron.entity_location (entity_id INT, location_id INT, PRIMARY KEY (entity_id, location_id))");
        dbInteractions.update(
                "CREATE TABLE viron.entity_delete_guard (id INT PRIMARY KEY, entity_id INT NOT NULL REFERENCES viron.entity(entity_id))");

        dbInteractions.update("INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (?, ?, ?)",
                ENTITY_ID, "Alice", "2026-01-01");
        dbInteractions.update("INSERT INTO viron.entity_location (entity_id, location_id) VALUES (?, ?)",
                ENTITY_ID, LOCATION_ID);
    }

    @AfterEach
    void tearDown() {
        ((HikariDataSource) dataSource).close();
    }

    // #194: the placement must come back when the entity delete it was cleared for does not happen.
    @Test
    void failedEntityDelete_insideATransaction_restoresTheClearedPlacement() {
        blockEntityDeletion();

        assertThatThrownBy(() -> transactionTemplate.executeWithoutResult(status -> {
            if (!entityRepository.deleteById(ENTITY_ID)) {
                throw new ServiceException("Failed to delete entity with id: " + ENTITY_ID);
            }
        })).isInstanceOf(ServiceException.class);

        assertThat(entityExists()).isTrue();
        assertThat(placementExists()).isTrue();
    }

    // The behaviour the boundary replaces: without one, the first delete commits on its own and
    // the surviving entity silently loses where it was.
    @Test
    void failedEntityDelete_withoutATransaction_leavesTheEntityWithoutItsPlacement() {
        blockEntityDeletion();

        assertThat(entityRepository.deleteById(ENTITY_ID)).isFalse();

        assertThat(entityExists()).isTrue();
        assertThat(placementExists()).isFalse();
    }

    @Test
    void successfulDelete_insideATransaction_commitsBothStatements() {
        transactionTemplate.executeWithoutResult(status -> {
            if (!entityRepository.deleteById(ENTITY_ID)) {
                throw new ServiceException("Failed to delete entity with id: " + ENTITY_ID);
            }
        });

        assertThat(entityExists()).isFalse();
        assertThat(placementExists()).isFalse();
    }

    /** Makes {@code DELETE FROM viron.entity} fail by pointing a foreign key at the row. */
    private void blockEntityDeletion() {
        dbInteractions.update("INSERT INTO viron.entity_delete_guard (id, entity_id) VALUES (?, ?)", 1, ENTITY_ID);
    }

    private boolean entityExists() {
        return dbInteractions.queryOne("SELECT entity_id FROM viron.entity WHERE entity_id = ?",
                rs -> rs.getInt(1), ENTITY_ID).isPresent();
    }

    private boolean placementExists() {
        return dbInteractions.queryOne("SELECT location_id FROM viron.entity_location WHERE entity_id = ?",
                rs -> rs.getInt(1), ENTITY_ID).isPresent();
    }

    private static DbConfig h2Config() {
        DbConfig config = new DbConfig();
        config.setDbUrl("jdbc:h2:mem:viron_transactionrollback;DB_CLOSE_DELAY=-1");
        config.setDbUsername("sa");
        config.setDbPassword("");
        return config;
    }
}
