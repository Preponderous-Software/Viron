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
import preponderous.viron.models.Grid;

@RestController
@RequestMapping("/grid")
@Slf4j
public class GridController {
    private final DbInteractions dbInteractions;

    @Autowired
    public GridController(DbInteractions dbInteractions) {
        this.dbInteractions = dbInteractions;
    }

    @RequestMapping("/get-all-grids")
    public ResponseEntity<List<Grid>> getAllGrids() {
        List<Grid> grids = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid");
        if (rs == null) {
            log.error("Error getting grids: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("grid_id");
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                grids.add(new Grid(id, rows, columns));
            }
        } catch (SQLException e) {
            log.error("Error getting grids: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(grids);
    }

    @RequestMapping("/get-grid-by-id/{id}")
    public ResponseEntity<Grid> getGridById(@PathVariable int id) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid WHERE grid_id = " + id);
        try {
            if (rs.next()) {
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                return ResponseEntity.ok(new Grid(id, rows, columns));
            }
        } catch (SQLException e) {
            log.error("Error getting grid by id: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping("/get-grids-in-environment/{environmentId}")
    public ResponseEntity<List<Grid>> getGridsForEnvironment(@PathVariable int environmentId) {
        List<Grid> grids = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")");
        if (rs == null) {
            log.error("Error getting grids for environment: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("grid_id");
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                grids.add(new Grid(id, rows, columns));
            }
        } catch (SQLException e) {
            log.error("Error getting grids for environment: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(grids);
    }

    @RequestMapping("/get-grid-of-entity/{entityId}")
    public ResponseEntity<Grid> getGridOfEntity(@PathVariable int entityId) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.location_grid WHERE location_id in (SELECT location_id FROM viron.entity_location WHERE entity_id = " + entityId + "))");
        try {
            if (rs.next()) {
                int id = rs.getInt("grid_id");
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                return ResponseEntity.ok(new Grid(id, rows, columns));
            }
        } catch (SQLException e) {
            log.error("Error getting grid of entity: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }
}
