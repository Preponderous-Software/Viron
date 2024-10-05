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
import preponderous.viron.models.Grid;

@RestController
@RequestMapping("/grid")
public class GridController {
    private final DbInteractions dbInteractions;

    @Autowired
    public GridController(DbInteractions dbInteractions) {
        this.dbInteractions = dbInteractions;
    }

    @RequestMapping("/get-all-grids")
    public List<Grid> getGrids() {
        List<Grid> grids = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid");
        try {
            while (rs.next()) {
                int id = rs.getInt("grid_id");
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                grids.add(new Grid(id, rows, columns));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grids;
    }

    @RequestMapping("/get-grid-by-id/{id}")
    public Grid getGridById(@PathVariable int id) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid WHERE grid_id = " + id);
        try {
            if (rs.next()) {
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                return new Grid(id, rows, columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/get-grids-in-environment/{environmentId}")
    public List<Grid> getGridsForEnvironment(@PathVariable int environmentId) {
        List<Grid> grids = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")");
        try {
            while (rs.next()) {
                int id = rs.getInt("grid_id");
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                grids.add(new Grid(id, rows, columns));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grids;
    }

    @RequestMapping("/get-grid-of-entity/{entityId}")
    public Grid getGridOfEntity(@PathVariable int entityId) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.location_grid WHERE location_id in (SELECT location_id FROM viron.entity_location WHERE entity_id = " + entityId + "))");
        try {
            if (rs.next()) {
                int id = rs.getInt("grid_id");
                int rows = rs.getInt("rows");
                int columns = rs.getInt("columns");
                return new Grid(id, rows, columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
