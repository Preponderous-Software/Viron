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
import preponderous.viron.models.Location;

@RestController
@RequestMapping("/location")
@Slf4j
public class LocationController {
    private final DbInteractions dbInteractions;

    @Autowired
    public LocationController(DbInteractions dbInteractions) {
        this.dbInteractions = dbInteractions;
    }

    @RequestMapping("/get-all-locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.location");
        if (rs == null) {
            log.error("Error getting locations: ResultSet is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("location_id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                locations.add(new Location(id, x, y));
                return ResponseEntity.ok(locations);
            }
        } catch (SQLException e) {
            log.error("Error getting locations: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping("/get-location-by-id/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable int id) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.location WHERE location_id = " + id);
        try {
            if (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                return ResponseEntity.ok(new Location(id, x, y));
            }
        } catch (SQLException e) {
            log.error("Error getting location by id: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping("/get-locations-in-environment/{environmentId}")
    public ResponseEntity<List<Location>> getLocationsInEnvironment(@PathVariable int environmentId) {
        List<Location> locations = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + "))");
        try {
            while (rs.next()) {
                int id = rs.getInt("location_id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                locations.add(new Location(id, x, y));
                return ResponseEntity.ok(locations);
            }
        } catch (SQLException e) {
            log.error("Error getting locations in environment: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping("/get-locations-in-grid/{gridId}")
    public ResponseEntity<List<Location>> getLocationsInGrid(@PathVariable int gridId) {
        List<Location> locations = new ArrayList<>();
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = " + gridId + ")");
        try {
            while (rs.next()) {
                int id = rs.getInt("location_id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                locations.add(new Location(id, x, y));
                return ResponseEntity.ok(locations);
            }
        } catch (SQLException e) {
            log.error("Error getting locations in grid: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping("/get-location-of-entity/{entityId}")
    public ResponseEntity<Location> getLocationOfEntity(@PathVariable int entityId) {
        ResultSet rs = dbInteractions.query("SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.entity_location WHERE entity_id = " + entityId + ")");
        try {
            if (rs.next()) {
                int location_id = rs.getInt("location_id");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                return ResponseEntity.ok(new Location(location_id, x, y));
            }
        } catch (SQLException e) {
            log.error("Error getting location of entity: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping("/add-entity-to-location/{entityId}/{locationId}")
    public ResponseEntity<Boolean> addEntityToLocation(@PathVariable int entityId, @PathVariable int locationId) {
        return ResponseEntity.ok(dbInteractions.update("INSERT INTO viron.entity_location (entity_id, location_id) VALUES (" + entityId + ", " + locationId + ")"));
    }

    @RequestMapping("/remove-entity-from-location/{entityId}/{locationId}")
    public ResponseEntity<Boolean> removeEntityFromLocation(@PathVariable int entityId, @PathVariable int locationId) {
        return ResponseEntity.ok(dbInteractions.update("DELETE FROM viron.entity_location WHERE entity_id = " + entityId + " AND location_id = " + locationId));
    }

    @RequestMapping("/remove-entity-from-current-location/{entityId}")
    public ResponseEntity<Boolean> removeEntityFromCurrentLocation(@PathVariable int entityId) {
        return ResponseEntity.ok(dbInteractions.update("DELETE FROM viron.entity_location WHERE entity_id = " + entityId));
    }
}