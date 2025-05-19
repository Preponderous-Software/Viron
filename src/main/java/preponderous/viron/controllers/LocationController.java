package preponderous.viron.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import preponderous.viron.models.Location;
import preponderous.viron.repositories.LocationRepository;

import java.util.List;

@RestController
@RequestMapping("/location")
@Slf4j
@RequiredArgsConstructor
public class LocationController {
    private final LocationRepository locationRepository;

    @RequestMapping("/get-all-locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        try {
            return ResponseEntity.ok(locationRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting locations: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping("/get-location-by-id/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable int id) {
        try {
            return locationRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            log.error("Error getting location by id: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/get-locations-in-environment/{environmentId}")
    public ResponseEntity<List<Location>> getLocationsInEnvironment(@PathVariable int environmentId) {
        try {
            return ResponseEntity.ok(locationRepository.findByEnvironmentId(environmentId));
        } catch (Exception e) {
            log.error("Error getting locations in environment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/get-locations-in-grid/{gridId}")
    public ResponseEntity<List<Location>> getLocationsInGrid(@PathVariable int gridId) {
        try {
            return ResponseEntity.ok(locationRepository.findByGridId(gridId));
        } catch (Exception e) {
            log.error("Error getting locations in grid: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/get-location-of-entity/{entityId}")
    public ResponseEntity<Location> getLocationOfEntity(@PathVariable int entityId) {
        try {
            return locationRepository.findByEntityId(entityId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            log.error("Error getting location of entity: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/add-entity-to-location/{entityId}/{locationId}")
    public ResponseEntity<Boolean> addEntityToLocation(@PathVariable int entityId, @PathVariable int locationId) {
        try {
            return ResponseEntity.ok(locationRepository.addEntityToLocation(entityId, locationId));
        } catch (Exception e) {
            log.error("Error adding entity to location: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/remove-entity-from-location/{entityId}/{locationId}")
    public ResponseEntity<Boolean> removeEntityFromLocation(@PathVariable int entityId, @PathVariable int locationId) {
        try {
            return ResponseEntity.ok(locationRepository.removeEntityFromLocation(entityId, locationId));
        } catch (Exception e) {
            log.error("Error removing entity from location: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/remove-entity-from-current-location/{entityId}")
    public ResponseEntity<Boolean> removeEntityFromCurrentLocation(@PathVariable int entityId) {
        try {
            return ResponseEntity.ok(locationRepository.removeEntityFromCurrentLocation(entityId));
        } catch (Exception e) {
            log.error("Error removing entity from current location: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}