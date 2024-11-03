package preponderous.viron.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.models.Grid;

@SpringBootTest
public class GridControllerTest {
    private DbInteractions dbInteractions;
    private GridController gridController;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        dbInteractions = Mockito.mock(DbInteractions.class);
        gridController = new GridController(dbInteractions);
    }
    
    @Test
    void testInitialization() {
        // execute
        gridController = new GridController(dbInteractions);

        // verify
        assertNotNull(gridController);
    }

    @Test
    void testGetAllGrids_SUCCESS() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.grid";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt("grid_id")).thenReturn(1).thenReturn(2);
        when(rs.getInt("rows")).thenReturn(1).thenReturn(2);
        when(rs.getInt("columns")).thenReturn(1).thenReturn(2);

        // execute
        ResponseEntity<List<Grid>> response = gridController.getAllGrids();

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getGridId());
        assertEquals(1, response.getBody().get(0).getRows());
        assertEquals(1, response.getBody().get(0).getColumns());
        assertEquals(2, response.getBody().get(1).getGridId());
        assertEquals(2, response.getBody().get(1).getRows());
        assertEquals(2, response.getBody().get(1).getColumns());
        Mockito.verify(dbInteractions).query(expectedQuery);        
    }

    @Test
    void testGetAllGrids_SQL_EXCEPTION() throws SQLException {
        // setup
        String expectedQuery = "SELECT * FROM viron.grid";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test Exception"));

        // execute
        ResponseEntity<List<Grid>> response = gridController.getAllGrids();

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridById_SUCCESS() throws SQLException {
        // setup
        int gridId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id = " + gridId;
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("grid_id")).thenReturn(gridId);
        when(rs.getInt("rows")).thenReturn(1);
        when(rs.getInt("columns")).thenReturn(1);

        // execute
        ResponseEntity<Grid> response = gridController.getGridById(gridId);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(gridId, response.getBody().getGridId());
        assertEquals(1, response.getBody().getRows());
        assertEquals(1, response.getBody().getColumns());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridById_SQL_EXCEPTION() throws SQLException {
        // setup
        int gridId = 1;
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id = " + gridId;
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test Exception"));

        // execute
        ResponseEntity<Grid> response = gridController.getGridById(gridId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridById_NOT_FOUND() throws SQLException {
        // setup
        int gridId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id = " + gridId;
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // execute
        ResponseEntity<Grid> response = gridController.getGridById(gridId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridsForEnvironment_SQL_EXCEPTION() throws SQLException {
        // setup
        int environmentId = 1;
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")";
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test Exception"));

        // execute
        ResponseEntity<List<Grid>> response = gridController.getGridsForEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridsForEnvironment_SUCCESS() throws SQLException {
        // setup
        int environmentId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt("grid_id")).thenReturn(1).thenReturn(2);
        when(rs.getInt("rows")).thenReturn(1).thenReturn(2);
        when(rs.getInt("columns")).thenReturn(1).thenReturn(2);

        // execute
        ResponseEntity<List<Grid>> response = gridController.getGridsForEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getGridId());
        assertEquals(1, response.getBody().get(0).getRows());
        assertEquals(1, response.getBody().get(0).getColumns());
        assertEquals(2, response.getBody().get(1).getGridId());
        assertEquals(2, response.getBody().get(1).getRows());
        assertEquals(2, response.getBody().get(1).getColumns());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridsForEnvironment_NOT_FOUND() throws SQLException {
        // setup
        int environmentId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // execute
        ResponseEntity<List<Grid>> response = gridController.getGridsForEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridOfEntity_SUCCESS() throws SQLException {
        // setup
        int entityId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.location_grid WHERE location_id in (SELECT location_id FROM viron.entity_location WHERE entity_id = " + entityId + "))";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("grid_id")).thenReturn(1);
        when(rs.getInt("rows")).thenReturn(1);
        when(rs.getInt("columns")).thenReturn(1);

        // execute
        ResponseEntity<Grid> response = gridController.getGridOfEntity(entityId);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getGridId());
        assertEquals(1, response.getBody().getRows());
        assertEquals(1, response.getBody().getColumns());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetGridOfEntity_NOT_FOUND() throws SQLException {
        // setup
        int entityId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.grid WHERE grid_id in (SELECT grid_id FROM viron.location_grid WHERE location_id in (SELECT location_id FROM viron.entity_location WHERE entity_id = " + entityId + "))";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // execute
        ResponseEntity<Grid> response = gridController.getGridOfEntity(entityId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

}