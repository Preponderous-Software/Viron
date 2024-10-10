// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.factories.EnvironmentFactory;
import preponderous.viron.models.Environment;

@RestController
@RequestMapping("/environment")
public class EnvironmentController {
    private final DbInteractions dbInteractions;
    private final EnvironmentFactory environmentFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EnvironmentController(DbInteractions dbInteractions, EnvironmentFactory environmentFactory) {
        this.dbInteractions = dbInteractions;
        this.environmentFactory = environmentFactory;
    }

    @RequestMapping("/get-all-environments")
    public List<Environment> getEnvironments() {
        List<Environment> environments = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.environment");
        try {
            while (rs.next()) {
                int id = rs.getInt("environment_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                environments.add(new Environment(id, name, creationDate));
            }
        } catch (SQLException e) {
            logger.error("Error getting environments: " + e.getMessage());
        }
        return environments;
    }

    @RequestMapping("/get-environment-by-id/{id}")
    public Environment getEnvironmentById(@PathVariable int id) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.environment WHERE environment_id = " + id);
        try {
            if (rs.next()) {
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                return new Environment(id, name, creationDate);
            }
        } catch (SQLException e) {
            logger.error("Error getting environment by id: " + e.getMessage());
        }
        return null;
    }

    @RequestMapping("/get-environment-of-entity/{entityId}")
    public Environment getEnvironmentOfEntity(@PathVariable int entityId) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.environment WHERE environment_id = (SELECT environment_id FROM viron.entity WHERE entity_id = " + entityId + ")");
        try {
            if (rs.next()) {
                int id = rs.getInt("environment_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                return new Environment(id, name, creationDate);
            }
        } catch (SQLException e) {
            logger.error("Error getting environment of entity: " + e.getMessage());
        }
        return null;
    }

    @RequestMapping("/create-environment/{name}/{numGrids}/{gridSize}")
    public Environment createEnvironment(@PathVariable String name, @PathVariable int numGrids, @PathVariable int gridSize) {
        try {
            return environmentFactory.createEnvironment(name, numGrids, gridSize);
        } catch (EnvironmentFactory.EnvironmentCreationException e) {
            logger.error("Error creating environment: " + e.getMessage());
            return null;
        }
    }

    @RequestMapping("/delete-environment/{id}")
    public boolean deleteEnvironment(@PathVariable int id) {
        // check if environment exists
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.environment WHERE environment_id = " + id);
        try {
            if (!rs.next()) {
                System.out.println("Environment with id " + id + " does not exist, cannot delete");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error checking if environment exists: " + e.getMessage());
            return false;
        }

        logger.info("Attempting to delete environment with id " + id);

        List<Integer> entityIds = new ArrayList<>();
        rs = dbInteractions.query("SELECT entity_id FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + id + ")))");
        try {
            while (rs.next()) {
                entityIds.add(rs.getInt("entity_id"));
            }
        } catch (SQLException e) {
            logger.error("Error getting entity ids: " + e.getMessage());
        }

        List<Integer> locationIds = new ArrayList<>();
        rs = dbInteractions.query("SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + id + ")");
        try {
            while (rs.next()) {
                locationIds.add(rs.getInt("location_id"));
            }
        } catch (SQLException e) {
            logger.error("Error getting location ids: " + e.getMessage());
        }

        List<Integer> gridIds = new ArrayList<>();
        rs = dbInteractions.query("SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + id);
        try {
            while (rs.next()) {
                gridIds.add(rs.getInt("grid_id"));
            }
        } catch (SQLException e) {
            logger.error("Error getting grid ids: " + e.getMessage());
        }

        // delete associations
        boolean success;
        for (int entityId : entityIds) {
            String query = "DELETE FRWOM viron.entity_location WHERE entity_id = " + entityId;
            success = dbInteractions.update(query);
            if (!success) {
                return false;
            }
        }

        for (int locationId : locationIds) {
            String query = "DELETE FROM viron.location_grid WHERE location_id = " + locationId;
            success = dbInteractions.update(query);
            if (!success) {
                logger.error("Error deleting location_grid with location id " + locationId);
                return false;
            }
        }

        for (int gridId : gridIds) {
            String query = "DELETE FROM viron.grid_environment WHERE grid_id = " + gridId;
            success = dbInteractions.update(query);
            if (!success) {
                logger.error("Error deleting grid_environment with grid id " + gridId);
                return false;
            }
        }

        // delete entities
        for (int entityId : entityIds) {
            String query = "DELETE FROM viron.entity WHERE entity_id = " + entityId;
            success = dbInteractions.update(query);
            if (!success) {
                logger.error("Error deleting entity with id " + entityId);
                return false;
            }
        }
        if (!entityIds.isEmpty()) {
            logger.info("Entities deleted: " + entityIds);
        }

        // delete locations
        for (int locationId : locationIds) {
            String query = "DELETE FROM viron.location WHERE location_id = " + locationId;
            success = dbInteractions.update(query);
            if (!success) {
                logger.error("Error deleting location with id " + locationId);
                return false;
            }
        }
        if (!locationIds.isEmpty()) {
            logger.info("Locations deleted: " + locationIds);
        }

        // delete grids
        for (int gridId : gridIds) {
            String query = "DELETE FROM viron.grid WHERE grid_id = " + gridId;
            success = dbInteractions.update(query);
            if (!success) {
                logger.error("Error deleting grid with id " + gridId);
                return false;
            }
        }
        if (!gridIds.isEmpty()) {
            logger.info("Grids deleted: " + gridIds);
        }

        // delete environment       
        String query = "DELETE FROM viron.environment WHERE environment_id = " + id;
        success = dbInteractions.update(query);
        if (!success) {
            return false;
        }
        logger.info("Environment with id " + id + " deleted");
        return true;
    }

    @RequestMapping("/set-environment-name/{id}/{name}")
    public boolean setEnvironmentName(@PathVariable int id, @PathVariable String name) {
        String query = "UPDATE viron.environment SET name = '" + name + "' WHERE environment_id = " + id;
        return dbInteractions.update(query);
    }
}