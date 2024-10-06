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
import preponderous.viron.models.Environment;

@RestController
@RequestMapping("/environment")
public class EnvironmentController {
    private final DbInteractions dbInteractions;

    @Autowired
    public EnvironmentController(DbInteractions dbInteractions) {
        this.dbInteractions = dbInteractions;
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    // TODO: implement 'create' endpoint

    // TODO: implement 'delete' endpoint

    // TODO: implement 'set name' endpoint
}