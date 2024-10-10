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
import preponderous.viron.factories.EntityFactory;
import preponderous.viron.models.Entity;

@RestController
@RequestMapping("/entity")
public class EntityController {
    private final DbInteractions dbInteractions;
    private final EntityFactory entityFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EntityController(DbInteractions dbInteractions, EntityFactory entityFactory) {
        this.dbInteractions = dbInteractions;
        this.entityFactory = entityFactory;
    }

    @RequestMapping("/get-all-entities")
    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity");
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            logger.error("Error getting entities: " + e.getMessage());
        }
        return entities;
    }

    @RequestMapping("/get-entity-by-id/{id}")
    public Entity getEntityById(@PathVariable int id) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id = " + id);
        try {
            if (rs.next()) {
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                return new Entity(id, name, creationDate);
            }
        } catch (SQLException e) {
            logger.error("Error getting entity by id: " + e.getMessage());
        }
        return null;
    }

    @RequestMapping("/get-entities-in-environment/{environmentId}")
    public List<Entity> getEntitiesInEnvironment(@PathVariable int environmentId) {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")))");
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            logger.error("Error getting entities in environment: " + e.getMessage());
        }
        return entities;
    }

    @RequestMapping("/get-entities-in-grid/{gridId}")
    public List<Entity> getEntitiesInGrid(@PathVariable int gridId) {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = " + gridId + "))");
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            logger.error("Error getting entities in grid: " + e.getMessage());
        }
        return entities;
    }
    
    @RequestMapping("/get-entities-in-location/{locationId}")
    public List<Entity> getEntitiesInLocation(@PathVariable int locationId) {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = " + locationId + ")");
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            logger.error("Error getting entities in location: " + e.getMessage());
        }
        return entities;
    }

    @RequestMapping("/get-entities-not-in-any-location")
    public List<Entity> getEntitiesNotInAnyLocation() {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)");
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            logger.error("Error getting entities not in any location: " + e.getMessage());
        }
        return entities;
    }

    @RequestMapping("/create-entity/{name}")
    public Entity createEntity(@PathVariable String name) {
        try {
            return entityFactory.createEntity(name);
        } catch (EntityFactory.EntityCreationException e) {
            logger.error("Error creating entity: " + e.getMessage());
            return null;
        }
    }

    @RequestMapping("/delete-entity/{id}")
    public boolean deleteEntity(@PathVariable int id) {
        String query = "DELETE FROM viron.entity WHERE entity_id = " + id;
        return dbInteractions.update(query);
    }

    @RequestMapping("/set-entity-name/{id}/{name}")
    public boolean setEntityName(@PathVariable int id, @PathVariable String name) {
        String query = "UPDATE viron.entity SET name = '" + name + "' WHERE entity_id = " + id;
        return dbInteractions.update(query);
    }
}
