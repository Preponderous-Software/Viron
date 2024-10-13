package preponderous.viron.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import preponderous.viron.models.Entity;
import preponderous.viron.models.Environment;
import preponderous.viron.models.Grid;
import preponderous.viron.models.Location;
import preponderous.viron.services.EntityService;
import preponderous.viron.services.EnvironmentService;
import preponderous.viron.services.GridService;
import preponderous.viron.services.LocationService;

@RestController
@RequestMapping("/debug")
public class DebugController {
    private final EntityService entityService;
    private final EnvironmentService environmentService;
    private final GridService gridService;
    private final LocationService locationService;

    public DebugController(EntityService entityService, EnvironmentService environmentService, GridService gridService, LocationService locationService) {
        this.entityService = entityService;
        this.environmentService = environmentService;
        this.gridService = gridService;
        this.locationService = locationService;
    }

    @RequestMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    // create sample data
    @RequestMapping("/create-sample-data")
    public ResponseEntity<Boolean> createSampleData() {
        // create an environment with one 10x10 grid
        Environment environment = environmentService.createEnvironment("Sample Environment", 1, 10);
        List<Grid> grids = gridService.getGridsInEnvironment(environment.getEnvironmentId());
        Grid grid = grids.get(0);
        List<Location> locations = locationService.getLocationsInGrid(grid.getGridId());

        // create ten entities and place them in the environment
        for (int i = 0; i < 10; i++) {
            Entity entity = entityService.createEntity("Entity " + i);
            
            // get random location in the grid
            int x = (int) (Math.random() * grid.getRows());
            int y = (int) (Math.random() * grid.getColumns());
            Location location = null;
            for (int j = 0; j < locations.size(); j++) {
                if (locations.get(j).getX() == x && locations.get(j).getY() == y) {
                    location = locations.get(j);
                    break;
                }
            }
            if (location == null) {
                return ResponseEntity.ok(false);
            }
            locationService.addEntityToLocation(entity.getEntityId(), location.getLocationId());
        }

        return ResponseEntity.ok(true);
    }
}
