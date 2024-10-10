package preponderous.viron.factories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.models.Environment;

@Component
public class EnvironmentFactory {
    private final DbInteractions dbInteractions;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EnvironmentFactory(DbInteractions dbInteractions) {
        this.dbInteractions = dbInteractions;
    }

    public Environment createEnvironment(String name, int numGrids, int gridSize) throws EnvironmentCreationException {
        logger.info("Attempting to create environment: '" + name + "' with " + numGrids + " grids of size " + gridSize);
        
        // create environment
        int id = getNextEnvironmentId();
        String creationDate = new java.util.Date().toString();
        String query = "INSERT INTO viron.environment (environment_id, name, creation_date) VALUES (" + id + ", '" + name + "', '" + creationDate + "')";
        boolean success = dbInteractions.update(query);
        if (!success) {
            logger.error("Failed to create environment");
            throw new EnvironmentCreationException("Failed to create environment");
        }

        // create grids
        List<Integer> gridIds = new ArrayList<>();
        for (int i = 0; i < numGrids; i++) {
            int nextGridId = getNextGridId();
            gridIds.add(nextGridId);

            query = "INSERT INTO viron.grid (grid_id, rows, columns) VALUES (" + nextGridId + ", " + gridSize + ", " + gridSize + ")";
            success = dbInteractions.update(query);
            if (!success) {
                logger.error("Failed to create grid");
                throw new EnvironmentCreationException("Failed to create grid");
            }

            // associate grid with environment
            query = "INSERT INTO viron.grid_environment (grid_id, environment_id) VALUES (" + nextGridId + ", " + id + ")";
            success = dbInteractions.update(query);
            if (!success) {
                logger.error("Failed to associate grid with environment");
                throw new EnvironmentCreationException("Failed to associate grid with environment");
            }

            // create locations
            List<Integer> locationIds = new ArrayList<>();
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    int locationId = getNextLocationId();
                    query = "INSERT INTO viron.location (location_id, x, y) VALUES (" + locationId + ", " + x + ", " + y + ")";
                    success = dbInteractions.update(query);
                    if (!success) {
                        logger.error("Failed to create location");
                        throw new EnvironmentCreationException("Failed to create location");
                    }

                    // associate location with grid
                    query = "INSERT INTO viron.location_grid (location_id, grid_id) VALUES (" + locationId + ", " + nextGridId + ")";
                    success = dbInteractions.update(query);
                    if (!success) {
                        logger.error("Failed to associate location with grid");
                        throw new EnvironmentCreationException("Failed to associate location with grid");
                    }
                    locationIds.add(locationId);
                }
            }
            logger.info("Locations created: " + locationIds);      
        }
        logger.info("Grids created: " + gridIds);

        Environment environment = new Environment(id, name, creationDate);
        logger.info("Successfully created environment: '" + name + "' with id: " + id + " and creation date: " + creationDate);
        
        return environment;
    }

    private int getNextEnvironmentId() {
        ResultSet rs = dbInteractions.query("SELECT nextval('viron.environment_id_seq')");
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getNextGridId() {
        ResultSet rs = dbInteractions.query("SELECT nextval('viron.grid_id_seq')");
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getNextLocationId() {
        ResultSet rs = dbInteractions.query("SELECT nextval('viron.location_id_seq')");
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public class EnvironmentCreationException extends RuntimeException {
        public EnvironmentCreationException(String message) {
            super(message);
        }
    }
}
