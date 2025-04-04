// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.factories.EntityFactory;
import preponderous.viron.models.Entity;

@RestController
@RequestMapping("/entity")
@Slf4j
public class EntityController {
    private final DbInteractions dbInteractions;
    private final EntityFactory entityFactory;

    @Autowired
    public EntityController(DbInteractions dbInteractions, EntityFactory entityFactory) {
        this.dbInteractions = dbInteractions;
        this.entityFactory = entityFactory;
    }

    @RequestMapping("/get-all-entities")
    public ResponseEntity<List<Entity>> getAllEntities() {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity");
        if (rs == null) {
            log.error("Error getting entities: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            log.error("Error getting entities: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(entities);
    }

    @RequestMapping("/get-entity-by-id/{id}")
    public ResponseEntity<Entity> getEntityById(@PathVariable int id) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id = " + id);
        if (rs == null) {
            log.error("Error getting entity by id: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            if (rs.next()) {
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                return ResponseEntity.ok(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            log.error("Error getting entity by id: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping("/get-entities-in-environment/{environmentId}")
    public ResponseEntity<List<Entity>> getEntitiesInEnvironment(@PathVariable int environmentId) {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")))");
        if (rs == null) {
            log.error("Error getting entities in environment: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            log.error("Error getting entities in environment: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(entities);
    }

    @RequestMapping("/get-entities-in-grid/{gridId}")
    public ResponseEntity<List<Entity>> getEntitiesInGrid(@PathVariable int gridId) {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = " + gridId + "))");
        if (rs == null) {
            log.error("Error getting entities in grid: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            log.error("Error getting entities in grid: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(entities);
    }
    
    @RequestMapping("/get-entities-in-location/{locationId}")
    public ResponseEntity<List<Entity>> getEntitiesInLocation(@PathVariable int locationId) {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = " + locationId + ")");
        if (rs == null) {
            log.error("Error getting entities in location: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            log.error("Error getting entities in location: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(entities);
    }

    @RequestMapping("/get-entities-not-in-any-location")
    public ResponseEntity<List<Entity>> getEntitiesNotInAnyLocation() {
        List<Entity> entities = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)");
        if (rs == null) {
            log.error("Error getting entities not in any location: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("entity_id");
                String name = rs.getString("name");
                String creationDate = rs.getString("creation_date");
                entities.add(new Entity(id, name, creationDate));
            }
        } catch (SQLException e) {
            log.error("Error getting entities not in any location: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(entities);
    }

    @RequestMapping("/create-entity/{name}")
    public ResponseEntity<Entity> createEntity(@PathVariable String name) {
        try {
            return ResponseEntity.ok(entityFactory.createEntity(name));
        } catch (EntityFactory.EntityCreationException e) {
            log.error("Error creating entity: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping("/delete-entity/{id}")
    public ResponseEntity<Boolean> deleteEntity(@PathVariable int id) {
        String query = "DELETE FROM viron.entity WHERE entity_id = " + id;
        return ResponseEntity.ok(dbInteractions.update(query));
    }

    @RequestMapping("/set-entity-name/{id}/{name}")
    public ResponseEntity<Boolean> setEntityName(@PathVariable int id, @PathVariable String name) {
        String query = "UPDATE viron.entity SET name = '" + name + "' WHERE entity_id = " + id;
        return ResponseEntity.ok(dbInteractions.update(query));
    }
}
