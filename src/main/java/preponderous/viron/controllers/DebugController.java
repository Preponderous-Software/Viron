package preponderous.viron.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/v1/debug")
public class DebugController {
    private final EntityService entityService;
    private final EnvironmentService environmentService;
    private final GridService gridService;
    private final LocationService locationService;

    List<String> entityNamePool = new ArrayList<>(Arrays.asList("Tom", "Jerry", "Spike", "Tyke", "Nibbles", "Butch", "Tuffy", "Lightning", "Mammy", "Quacker", "Toodles", "Droopy", "Screwy", "Meathead", "George", "Dripple", "McWolf"));

    public DebugController(EntityService entityService, EnvironmentService environmentService, GridService gridService, LocationService locationService) {
        this.entityService = entityService;
        this.environmentService = environmentService;
        this.gridService = gridService;
        this.locationService = locationService;
    }

    /**
     * Creates a sample environment with a single 10x10 grid and places ten entities in random, valid locations within the grid.
     * It ensures the entities are properly created and assigned to valid locations in the grid.
     *
     * @return a ResponseEntity containing a Boolean value, true if the sample data was created successfully, false otherwise.
     */
    @PostMapping("/create-sample-data")
    public ResponseEntity<Boolean> createSampleData() {
        // create an environment with one 10x10 grid
        Environment environment = environmentService.createEnvironment("Sample Environment", 1, 10);
        List<Grid> grids = gridService.getGridsInEnvironment(environment.getEnvironmentId());
        Grid grid = grids.getFirst();
        List<Location> locations = locationService.getLocationsInGrid(grid.getGridId());

        // create ten entities and place them in the environment
        for (int i = 0; i < 10; i++) {
            Entity entity = entityService.createEntity(entityNamePool.get(i));

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

    /**
     * Creates an environment with a single grid of fixed size, generates a random entity,
     * and places the entity at a random valid location within the grid.
     *
     * @param environmentName the name of the environment to be created
     * @return a ResponseEntity containing a Boolean value, true if the entity was successfully created
     *         and placed in a location, false if the operation failed
     */
    @PostMapping("/create-world-and-place-entity/{environmentName}")
    public ResponseEntity<Boolean> createWorldAndPlaceEntity(@PathVariable String environmentName) {
        // create an environment
        int numGrids = 1;
        int gridSize = 5;
        Environment environment = environmentService.createEnvironment(environmentName, numGrids, gridSize);
        System.out.println("Environment created: " + environment.getName());

        // get grid info
        List<Grid> grids = gridService.getGridsInEnvironment(environment.getEnvironmentId());
        Grid grid = grids.get(0);
        System.out.println("Grid in environment has " + grid.getRows() + " rows and " + grid.getColumns() + " columns");

        // create an entity
        String entityName = entityNamePool.get((int) (Math.random() * entityNamePool.size()));
        Entity entity = entityService.createEntity(entityName);
        System.out.println("Entity created: " + entity.getName());

        // place entity in grid
        int entityRow = (int) (Math.random() * grid.getRows());
        int entityColumn = (int) (Math.random() * grid.getColumns());
        List<Location> locations = locationService.getLocationsInGrid(grid.getGridId());
        Location location = null;
        for (Location l : locations) {
            if (l.getX() == entityRow && l.getY() == entityColumn) {
                location = l;
                break;
            }
        }
        if (location == null) {
            // exit
            System.out.println("Location not found. Exiting...");
        }
        locationService.addEntityToLocation(entity.getEntityId(), location.getLocationId());
        System.out.println("Entity placed in grid at row " + entityRow + " and column " + entityColumn);
        return ResponseEntity.ok(true);
    }
}