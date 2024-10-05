// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import preponderous.viron.config.VironConfig;

/**
 * Postgres database interactions.
 */
@Component
public class DbInteractions {
    private Connection connection;
    private VironConfig vironConfig;

    @Autowired
    public DbInteractions(VironConfig vironConfig) {
        this.vironConfig = vironConfig;
        this.connection = connect();
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet query(String query) {
        try {
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(String query) {
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * Connect to the database.
    * @return Connection
    */
    private Connection connect() {
        try {
            connection = DriverManager.getConnection(vironConfig.getDbUrl(), vironConfig.getDbUsername(), vironConfig.getDbPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "postgres";
        VironConfig vironConfig = new VironConfig();
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
            e.printStackTrace();
        }
    }
}
