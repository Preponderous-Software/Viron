package preponderous.viron.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import preponderous.viron.models.Location;
import preponderous.viron.repositories.LocationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LocationControllerTest {
    private LocationRepository locationRepository;
    private LocationController locationController;

    @BeforeEach
    void setUp() {
        locationRepository = Mockito.mock(LocationRepository.class);
        locationController = new LocationController(locationRepository);
    }

    @Test
    void testInitialization() {
        assertNotNull(locationController);
    }

    @Test
    void testGetAllLocations() {
        // setup
        List<Location> locations = Arrays.asList(
                new Location(1, 10, 20)
        );
        when(locationRepository.findAll()).thenReturn(locations);

        // execute
        ResponseEntity<List<Location>> response = locationController.getAllLocations();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
        verify(locationRepository).findAll();
    }

    @Test
    void testGetAllLocationsException() {
        // setup
        when(locationRepository.findAll()).thenThrow(new RuntimeException());

        // execute
        ResponseEntity<List<Location>> response = locationController.getAllLocations();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).findAll();
    }

    @Test
    void testGetLocationById() {
        // setup
        Location location = new Location(1, 10, 20);
        when(locationRepository.findById(1)).thenReturn(Optional.of(location));

        // execute
        ResponseEntity<Location> response = locationController.getLocationById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getLocationId());
        verify(locationRepository).findById(1);
    }

    @Test
    void testGetLocationByIdNotFound() {
        // setup
        when(locationRepository.findById(1)).thenReturn(Optional.empty());

        // execute
        ResponseEntity<Location> response = locationController.getLocationById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).findById(1);
    }

    @Test
    void testGetLocationsInEnvironment() {
        // setup
        List<Location> locations = Arrays.asList(
                new Location(1, 10, 20)
        );
        when(locationRepository.findByEnvironmentId(1)).thenReturn(locations);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
        verify(locationRepository).findByEnvironmentId(1);
    }

    @Test
    void testGetLocationsInEnvironmentException() {
        // setup
        when(locationRepository.findByEnvironmentId(1)).thenThrow(new RuntimeException());

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).findByEnvironmentId(1);
    }

    @Test
    void testGetLocationsInGrid() {
        // setup
        List<Location> locations = Arrays.asList(
                new Location(1, 10, 20)
        );
        when(locationRepository.findByGridId(1)).thenReturn(locations);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
        verify(locationRepository).findByGridId(1);
    }

    @Test
    void testGetLocationsInGridException() {
        // setup
        when(locationRepository.findByGridId(1)).thenThrow(new RuntimeException());

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).findByGridId(1);
    }

    @Test
    void testGetLocationOfEntity() {
        // setup
        Location location = new Location(1, 10, 20);
        when(locationRepository.findByEntityId(1)).thenReturn(Optional.of(location));

        // execute
        ResponseEntity<Location> response = locationController.getLocationOfEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getLocationId());
        verify(locationRepository).findByEntityId(1);
    }

    @Test
    void testGetLocationOfEntityNotFound() {
        // setup
        when(locationRepository.findByEntityId(1)).thenReturn(Optional.empty());

        // execute
        ResponseEntity<Location> response = locationController.getLocationOfEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).findByEntityId(1);
    }

    @Test
    void testAddEntityToLocation() {
        // setup
        when(locationRepository.addEntityToLocation(1, 1)).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.addEntityToLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(locationRepository).addEntityToLocation(1, 1);
    }

    @Test
    void testAddEntityToLocationException() {
        // setup
        when(locationRepository.addEntityToLocation(1, 1)).thenThrow(new RuntimeException());

        // execute
        ResponseEntity<Boolean> response = locationController.addEntityToLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).addEntityToLocation(1, 1);
    }

    @Test
    void testRemoveEntityFromLocation() {
        // setup
        when(locationRepository.removeEntityFromLocation(1, 1)).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(locationRepository).removeEntityFromLocation(1, 1);
    }

    @Test
    void testRemoveEntityFromLocationException() {
        // setup
        when(locationRepository.removeEntityFromLocation(1, 1)).thenThrow(new RuntimeException());

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).removeEntityFromLocation(1, 1);
    }

    @Test
    void testRemoveEntityFromCurrentLocation() {
        // setup
        when(locationRepository.removeEntityFromCurrentLocation(1)).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromCurrentLocation(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(locationRepository).removeEntityFromCurrentLocation(1);
    }

    @Test
    void testRemoveEntityFromCurrentLocationException() {
        // setup
        when(locationRepository.removeEntityFromCurrentLocation(1)).thenThrow(new RuntimeException());

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromCurrentLocation(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(locationRepository).removeEntityFromCurrentLocation(1);
    }
}