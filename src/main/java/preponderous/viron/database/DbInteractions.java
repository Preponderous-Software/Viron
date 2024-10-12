// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import preponderous.viron.config.DbConfig;

/**
 * Postgres database interactions.
 */
@Component
public class DbInteractions {
    private Connection connection;
    private final DbConfig dbConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DbInteractions(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
        this.connection = connect();
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet query(String query) {
        try {
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            logger.error("Error executing query: " + e.getMessage());
        }
        return null;
    }

    public boolean update(String query) {
        try {
            int rowCount = connection.createStatement().executeUpdate(query);
            return rowCount > 0;
        } catch (SQLException e) {
            logger.error("Error executing update: " + e.getMessage());
        }
        return false;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Error closing connection: " + e.getMessage());
        }
    }

    /**
    * Connect to the database.
    * @return Connection
    */
    public Connection connect() {
        try {
            connection = DriverManager.getConnection(dbConfig.getDbUrl(), dbConfig.getDbUsername(), dbConfig.getDbPassword());
        } catch (SQLException e) {
            logger.error("Error connecting to the database: " + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "postgres";
        DbConfig vironConfig = new DbConfig();
        vironConfig.setDbUrl(url);
        vironConfig.setDbUsername(username);
        vironConfig.setDbPassword(password);
        DbInteractions dbInteractions = new DbInteractions(vironConfig);
        Connection connection = dbInteractions.getConnection();

        if (connection != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to connect to the database.");
        }

        // list tables
        ResultSet rs = dbInteractions.query("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';");
        try {
            while (rs.next()) {
                System.out.println(rs.getString("table_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error listing tables: " + e.getMessage());
        }
    }
}
