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
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getAllLocations();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
    }
    
    @Test
    void testGetAllLocationsSQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getAllLocations();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testGetLocationById() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getLocationId());
    }

    @Test
    void testGetLocationByIdSQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testGetLocationsInEnvironment() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
    }

    @Test
    void testGetLocationsInEnvironmentSQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testGetLocationsInGrid() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getLocationId());
    }

    @Test
    void testGetLocationsInGridSQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<List<Location>> response = locationController.getLocationsInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testGetLocationOfEntity() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("location_id")).thenReturn(1);
        when(rs.getInt("x")).thenReturn(10);
        when(rs.getInt("y")).thenReturn(20);
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationOfEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getLocationId());
    }

    @Test
    void testGetLocationOfEntitySQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.next()).thenThrow(new SQLException());
        when(dbInteractions.query(anyString())).thenReturn(rs);

        // execute
        ResponseEntity<Location> response = locationController.getLocationOfEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testAddEntityToLocation() {
        // setup
        when(dbInteractions.update(anyString())).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.addEntityToLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void testRemoveEntityFromLocation() {
        // setup
        when(dbInteractions.update(anyString())).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromLocation(1, 1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void testRemoveEntityFromCurrentLocation() {
        // setup
        when(dbInteractions.update(anyString())).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = locationController.removeEntityFromCurrentLocation(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }
}