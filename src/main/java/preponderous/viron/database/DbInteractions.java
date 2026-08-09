// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

/**
 * Postgres database interactions.
 *
 * <p>Every call borrows a connection from the pooled {@link DataSource} supplied by
 * {@link preponderous.viron.config.DataSourceConfig} and returns it before the call ends, so
 * concurrent requests never share one {@link Connection} (a {@code Connection} is not required
 * to be thread-safe, and the single long-lived connection this class used to hold was shared by
 * every request).
 *
 * <p>Connections are borrowed through {@link DataSourceUtils} rather than
 * {@code DataSource.getConnection()} directly. Outside a transaction that behaves exactly like
 * borrowing and returning a pooled connection; inside one, the connection already bound to the
 * transaction is reused and its release is deferred to the transaction manager. That is what
 * makes it possible to place transaction boundaries around the multi-statement write paths
 * tracked in #194 without changing this class again.
 *
 * <p>A failure to obtain a connection at all (database down, credentials wrong, pool exhausted)
 * surfaces as an unchecked
 * {@link org.springframework.jdbc.CannotGetJdbcConnectionException} rather than an empty result,
 * so an outage is reported as a 500 instead of being mistaken for missing data.
 */
@Component
@Slf4j
public class DbInteractions {
    private final DataSource dataSource;

    @Autowired
    public DbInteractions(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Execute a parameterized SELECT and map every returned row.
     *
     * <p>The query is run through a {@link PreparedStatement} so caller-supplied
     * values are bound as parameters rather than concatenated into SQL. The
     * connection, statement and result set are owned here and released before returning, so no
     * JDBC resource ever reaches the caller.
     *
     * @param query  SQL with {@code ?} placeholders for each parameter
     * @param mapper maps each row to a result object
     * @param params values to bind to the placeholders, in order
     * @param <T>    mapped result type
     * @return the mapped rows in result-set order, or an empty list if the query failed
     */
    public <T> List<T> query(String query, RowMapper<T> mapper, Object... params) {
        List<T> results = new ArrayList<>();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            bindParameters(statement, params);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Error executing query: {}", e.getMessage());
            // Discard any partially mapped rows rather than reporting a truncated result.
            return new ArrayList<>();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return results;
    }

    /**
     * Execute a parameterized SELECT and map its first row, if any.
     *
     * <p>Behaves like {@link #query(String, RowMapper, Object...)} but stops after the
     * first row; any further rows the query happens to return are ignored.
     *
     * @param query  SQL with {@code ?} placeholders for each parameter
     * @param mapper maps the first row to a result object
     * @param params values to bind to the placeholders, in order
     * @param <T>    mapped result type
     * @return the mapped first row, or an empty {@link Optional} if there were no rows or the query failed
     */
    public <T> Optional<T> queryOne(String query, RowMapper<T> mapper, Object... params) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            bindParameters(statement, params);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(mapper.map(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Error executing query: {}", e.getMessage());
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return Optional.empty();
    }

    /**
     * Execute a parameterized INSERT/UPDATE/DELETE.
     *
     * <p>Run through a {@link PreparedStatement} (parameters bound, not concatenated)
     * inside a try-with-resources so the statement is always closed, and the borrowed
     * connection is always returned to the pool.
     *
     * @param query  SQL with {@code ?} placeholders for each parameter
     * @param params values to bind to the placeholders, in order
     * @return {@code true} if at least one row was affected, {@code false} otherwise (including on error)
     */
    public boolean update(String query, Object... params) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            bindParameters(statement, params);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Error executing update: {}", e.getMessage());
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return false;
    }

    private void bindParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}
