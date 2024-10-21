package preponderous.viron.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import preponderous.viron.database.DbInteractions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import preponderous.viron.models.Location;

@SpringBootTest
public class LocationControllerTest {
    private DbInteractions dbInteractions;
    private LocationController locationController;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        dbInteractions = Mockito.mock(DbInteractions.class);
        locationController = new LocationController(dbInteractions);
    }
    
    @Test
    void testInitialization() {
        // execute
        locationController = new LocationController(dbInteractions);

        // verify
        assertNotNull(locationController);
    }

    @Test
    void testGetAllLocations() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getAllLocations();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }
    
    @Test
    void testGetAllLocationsSQLException() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getAllLocations();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationById() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id = 1";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getLocationId());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationByIdSQLException() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id = 1";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationsInEnvironment() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1))";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationsInEnvironmentSQLException() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1))";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationsInGrid() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1)"; 
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationsInGridSQLException() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1)";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationOfEntity() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.entity_location WHERE entity_id = 1)";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationOfEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getLocationId());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetLocationOfEntitySQLException() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.location WHERE location_id in (SELECT location_id FROM viron.entity_location WHERE entity_id = 1)";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationOfEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testAddEntityToLocation() {
        // setup
        String expectedQuery = "INSERT INTO viron.entity_location (entity_id, location_id) VALUES (1, 1)";
        when(dbInteractions.update(expectedQuery)).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.addEntityToLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        Mockito.verify(dbInteractions).update(expectedQuery);
    }

    @Test
    void testRemoveEntityFromLocation() {
        // setup
        String expectedQuery = "DELETE FROM viron.entity_location WHERE entity_id = 1 AND location_id = 1";
        when(dbInteractions.update(expectedQuery)).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        Mockito.verify(dbInteractions).update(expectedQuery);
    }

    @Test
    void testRemoveEntityFromCurrentLocation() {
        // setup
        String expectedQuery = "DELETE FROM viron.entity_location WHERE entity_id = 1";
        when(dbInteractions.update(expectedQuery)).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromCurrentLocation(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        Mockito.verify(dbInteractions).update(expectedQuery);
    }
}