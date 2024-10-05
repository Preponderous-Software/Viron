// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.models.Entity;

@RestController
@RequestMapping("/entity")
public class EntityController {
    private final DbInteractions dbInteractions;

    @Autowired
    public EntityController(DbInteractions dbInteractions) {
        this.dbInteractions = dbInteractions;
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return entities;
    }
}
