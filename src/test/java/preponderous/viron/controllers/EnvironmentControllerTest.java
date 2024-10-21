package preponderous.viron.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.factories.EnvironmentFactory;
import preponderous.viron.models.Environment;

@SpringBootTest
public class EnvironmentControllerTest {
    private DbInteractions dbInteractions;
    private EnvironmentFactory environmentFactory;
    private EnvironmentController environmentController;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        dbInteractions = Mockito.mock(DbInteractions.class);
        environmentFactory = Mockito.mock(EnvironmentFactory.class);
        environmentController = new EnvironmentController(dbInteractions, environmentFactory);
    }
    
    @Test
    void testInitialization() {
        // execute
        EnvironmentController environmentController = new EnvironmentController(dbInteractions, environmentFactory);

        // verify
        assertNotNull(environmentController);
    }

    @Test
    void testGetAllEnvironments_SUCCESS() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt("environment_id")).thenReturn(1).thenReturn(2);
        when(rs.getString("name")).thenReturn("Env1").thenReturn("Env2");
        when(rs.getString("creation_date")).thenReturn("2023-01-01").thenReturn("2023-01-02");

        // execute
        ResponseEntity<List<Environment>> response = environmentController.getAllEnvironments();

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Env1", response.getBody().get(0).getName());
        assertEquals("Env2", response.getBody().get(1).getName());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetAllEnvironments_SQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<List<Environment>> response = environmentController.getAllEnvironments();

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetAllEnvironments_EmptyResultSet() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // execute
        ResponseEntity<List<Environment>> response = environmentController.getAllEnvironments();

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetEnvironmentById_SUCCESS() throws SQLException {
        // setup
        int id = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment WHERE environment_id = " + id;
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("environment_id")).thenReturn(id);
        when(rs.getString("name")).thenReturn("Env1");
        when(rs.getString("creation_date")).thenReturn("2023-01-01");

        // execute
        ResponseEntity<Environment> response = environmentController.getEnvironmentById(id);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Env1", response.getBody().getName());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetEnvironmentById_SQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        int id = 1;
        String expectedQuery = "SELECT * FROM viron.environment WHERE environment_id = " + id;
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Environment> response = environmentController.getEnvironmentById(id);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetEnvironmentByName_SUCCESS() throws SQLException {
        // setup
        String name = "Env1";
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment WHERE name = '" + name + "'";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("environment_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn(name);
        when(rs.getString("creation_date")).thenReturn("2023-01-01");

        // execute
        ResponseEntity<Environment> response = environmentController.getEnvironmentByName(name);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(name, response.getBody().getName());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetEnvironmentByName_SQLException() throws SQLException {
        // setup
        ResultSet rs = Mockito.mock(ResultSet.class);
        String name = "Env1";
        String expectedQuery = "SELECT * FROM viron.environment WHERE name = '" + name + "'";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Environment> response = environmentController.getEnvironmentByName(name);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testCreateEnvironment_SUCCESS() throws EnvironmentFactory.EnvironmentCreationException {
        // setup
        String name = "Env1";
        int numGrids = 5;
        int gridSize = 10;
        Environment expectedEnvironment = new Environment(1, name, "2023-01-01");
        when(environmentFactory.createEnvironment(name, numGrids, gridSize)).thenReturn(expectedEnvironment);

        // execute
        ResponseEntity<Environment> response = environmentController.createEnvironment(name, numGrids, gridSize);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(name, response.getBody().getName());
        Mockito.verify(environmentFactory).createEnvironment(name, numGrids, gridSize);
    }

    @Test
    void testCreateEnvironment_EnvironmentCreationException() throws EnvironmentFactory.EnvironmentCreationException {
        // setup
        String name = "Env1";
        int numGrids = 5;
        int gridSize = 10;
        when(environmentFactory.createEnvironment(name, numGrids, gridSize)).thenThrow(Mockito.mock(EnvironmentFactory.EnvironmentCreationException.class));

        // execute
        ResponseEntity<Environment> response = environmentController.createEnvironment(name, numGrids, gridSize);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(environmentFactory).createEnvironment(name, numGrids, gridSize);
    }

    @Test
    void testDeleteEnvironment_SUCCESS() throws SQLException {
        // setup
        int environmentId = 1;
        List<Integer> entityIds = List.of(1, 2, 3);
        List<Integer> locationIds = List.of(1, 2);
        List<Integer> gridIds = List.of(1, 2);
        List<String> expectedQueries = List.of(
            "SELECT * FROM viron.environment WHERE environment_id = " + environmentId,
            "SELECT entity_id FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")))",
            "SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")",
            "SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId
        );
        List<String> expectedDeletions = List.of(
            "DELETE FROM viron.entity_location WHERE entity_id = " + entityIds.get(0),
            "DELETE FROM viron.entity_location WHERE entity_id = " + entityIds.get(1),
            "DELETE FROM viron.entity_location WHERE entity_id = " + entityIds.get(2),
            "DELETE FROM viron.location_grid WHERE location_id = " + locationIds.get(0),
            "DELETE FROM viron.location_grid WHERE location_id = " + locationIds.get(1),
            "DELETE FROM viron.grid_environment WHERE grid_id = " + gridIds.get(0),
            "DELETE FROM viron.grid_environment WHERE grid_id = " + gridIds.get(1),
            "DELETE FROM viron.entity WHERE entity_id = " + entityIds.get(0),
            "DELETE FROM viron.entity WHERE entity_id = " + entityIds.get(1),
            "DELETE FROM viron.entity WHERE entity_id = " + entityIds.get(2),
            "DELETE FROM viron.location WHERE location_id = " + locationIds.get(0),
            "DELETE FROM viron.location WHERE location_id = " + locationIds.get(1),
            "DELETE FROM viron.grid WHERE grid_id = " + gridIds.get(0),
            "DELETE FROM viron.grid WHERE grid_id = " + gridIds.get(1),
            "DELETE FROM viron.environment WHERE environment_id = " + environmentId
        );
        ResultSet rs1 = Mockito.mock(ResultSet.class);
        ResultSet rs2 = Mockito.mock(ResultSet.class);
        ResultSet rs3 = Mockito.mock(ResultSet.class);
        ResultSet rs4 = Mockito.mock(ResultSet.class);
        
        when(dbInteractions.query(expectedQueries.get(0))).thenReturn(rs1);
        when(rs1.next()).thenReturn(true);
        when(rs1.getInt("environment_id")).thenReturn(environmentId);
        when(dbInteractions.query(expectedQueries.get(1))).thenReturn(rs2);
        when(rs2.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs2.getInt("entity_id")).thenReturn(entityIds.get(0)).thenReturn(entityIds.get(1)).thenReturn(entityIds.get(2));
        when(dbInteractions.query(expectedQueries.get(2))).thenReturn(rs3);
        when(rs3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs3.getInt("location_id")).thenReturn(locationIds.get(0)).thenReturn(locationIds.get(1));
        when(dbInteractions.query(expectedQueries.get(3))).thenReturn(rs4);
        when(rs4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs4.getInt("grid_id")).thenReturn(gridIds.get(0)).thenReturn(gridIds.get(1));

        when(dbInteractions.update(expectedDeletions.get(0))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(1))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(2))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(3))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(4))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(5))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(6))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(7))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(8))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(9))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(10))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(11))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(12))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(13))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(14))).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = environmentController.deleteEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        for (String query : expectedQueries) {
            Mockito.verify(dbInteractions).query(query);
        }
        for (String query : expectedDeletions) {
            Mockito.verify(dbInteractions).update(query);
        }
    }

    @Test
    void testDeleteEnvironment_SQLException_1() throws SQLException {
        // setup
        int environmentId = 1;
        List<String> expectedQueries = List.of(
            "SELECT * FROM viron.environment WHERE environment_id = " + environmentId
        );
        ResultSet rs1 = Mockito.mock(ResultSet.class);
        
        when(dbInteractions.query(expectedQueries.get(0))).thenReturn(rs1);
        when(rs1.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Boolean> response = environmentController.deleteEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQueries.get(0));
    }

    @Test
    void testDeleteEnvironment_SQLException_2() throws SQLException {
        // setup
        int environmentId = 1;
        List<String> expectedQueries = List.of(
            "SELECT * FROM viron.environment WHERE environment_id = " + environmentId,
            "SELECT entity_id FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")))"
        );
        ResultSet rs1 = Mockito.mock(ResultSet.class);
        ResultSet rs2 = Mockito.mock(ResultSet.class);
        
        when(dbInteractions.query(expectedQueries.get(0))).thenReturn(rs1);
        when(rs1.next()).thenReturn(true);
        when(rs1.getInt("environment_id")).thenReturn(environmentId);
        when(dbInteractions.query(expectedQueries.get(1))).thenReturn(rs2);
        when(rs2.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Boolean> response = environmentController.deleteEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
        for (String query : expectedQueries) {
            Mockito.verify(dbInteractions).query(query);
        }
    }

    @Test
    void testDeleteEnvironment_SQLException_3() throws SQLException {
        // setup
        int environmentId = 1;
        List<String> expectedQueries = List.of(
            "SELECT * FROM viron.environment WHERE environment_id = " + environmentId,
            "SELECT entity_id FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")))",
            "SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")"
        );

        ResultSet rs1 = Mockito.mock(ResultSet.class);
        ResultSet rs2 = Mockito.mock(ResultSet.class);
        ResultSet rs3 = Mockito.mock(ResultSet.class);
        
        when(dbInteractions.query(expectedQueries.get(0))).thenReturn(rs1);
        when(rs1.next()).thenReturn(true);
        when(rs1.getInt("environment_id")).thenReturn(environmentId);
        when(dbInteractions.query(expectedQueries.get(1))).thenReturn(rs2);
        when(rs2.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs2.getInt("entity_id")).thenReturn(1).thenReturn(2).thenReturn(3);
        when(dbInteractions.query(expectedQueries.get(2))).thenReturn(rs3);
        when(rs3.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Boolean> response = environmentController.deleteEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
        for (String query : expectedQueries) {
            Mockito.verify(dbInteractions).query(query);
        }
    }

    @Test
    void testDeleteEnvironment_SQLException_4() throws SQLException {
        // setup
        int environmentId = 1;
        List<String> expectedQueries = List.of(
            "SELECT * FROM viron.environment WHERE environment_id = " + environmentId,
            "SELECT entity_id FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")))",
            "SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")",
            "SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId
        );

        ResultSet rs1 = Mockito.mock(ResultSet.class);
        ResultSet rs2 = Mockito.mock(ResultSet.class);
        ResultSet rs3 = Mockito.mock(ResultSet.class);
        ResultSet rs4 = Mockito.mock(ResultSet.class);
        
        when(dbInteractions.query(expectedQueries.get(0))).thenReturn(rs1);
        when(rs1.next()).thenReturn(true);
        when(rs1.getInt("environment_id")).thenReturn(environmentId);
        when(dbInteractions.query(expectedQueries.get(1))).thenReturn(rs2);
        when(rs2.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs2.getInt("entity_id")).thenReturn(1).thenReturn(2).thenReturn(3);
        when(dbInteractions.query(expectedQueries.get(2))).thenReturn(rs3);
        when(rs3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs3.getInt("location_id")).thenReturn(1).thenReturn(2);
        when(dbInteractions.query(expectedQueries.get(3))).thenReturn(rs4);
        when(rs4.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Boolean> response = environmentController.deleteEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
        for (String query : expectedQueries) {
            Mockito.verify(dbInteractions).query(query);
        }
    }

    @Test
    void testDeleteEnvironment_EmptyResultSet() throws SQLException {
        // setup
        int environmentId = 1;
        List<String> expectedQueries = List.of(
            "SELECT * FROM viron.environment WHERE environment_id = " + environmentId,
            "SELECT entity_id FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")))",
            "SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId + ")",
            "SELECT grid_id FROM viron.grid_environment WHERE environment_id = " + environmentId
        );
        List<String> expectedDeletions = List.of(
            "DELETE FROM viron.entity_location WHERE entity_id = 1",
            "DELETE FROM viron.entity_location WHERE entity_id = 2",
            "DELETE FROM viron.entity_location WHERE entity_id = 3",
            "DELETE FROM viron.location_grid WHERE location_id = 1"
        );
        ResultSet rs1 = Mockito.mock(ResultSet.class);
        ResultSet rs2 = Mockito.mock(ResultSet.class);
        ResultSet rs3 = Mockito.mock(ResultSet.class);
        ResultSet rs4 = Mockito.mock(ResultSet.class);
        
        when(dbInteractions.query(expectedQueries.get(0))).thenReturn(rs1);
        when(rs1.next()).thenReturn(true);
        when(rs1.getInt("environment_id")).thenReturn(environmentId);
        when(dbInteractions.query(expectedQueries.get(1))).thenReturn(rs2);
        when(rs2.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs2.getInt("entity_id")).thenReturn(1).thenReturn(2).thenReturn(3);
        when(dbInteractions.query(expectedQueries.get(2))).thenReturn(rs3);
        when(rs3.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs3.getInt("location_id")).thenReturn(1).thenReturn(2);
        when(dbInteractions.query(expectedQueries.get(3))).thenReturn(rs4);
        when(rs4.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs4.getInt("grid_id")).thenReturn(1).thenReturn(2);

        when(dbInteractions.update(expectedDeletions.get(0))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(1))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(2))).thenReturn(true);
        when(dbInteractions.update(expectedDeletions.get(3))).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = environmentController.deleteEnvironment(environmentId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
        for (String query : expectedQueries) {
            Mockito.verify(dbInteractions).query(query);
        }
        for (String query : expectedDeletions) {
            Mockito.verify(dbInteractions).update(query);
        }
    }
        

    // @Test
    // void testDeleteEnvironment_SQLException() throws SQLException {
    //     // TODO: implement
    // }

    @Test
    void testSetEnvironmentName_SUCCESS() {
        // setup
        int id = 1;
        String name = "NewEnvName";
        String expectedQuery = "UPDATE viron.environment SET name = '" + name + "' WHERE environment_id = " + id;
        when(dbInteractions.update(expectedQuery)).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = environmentController.setEnvironmentName(id, name);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        Mockito.verify(dbInteractions).update(expectedQuery);
    }

    @Test
    void testSetEnvironmentName_Failure() {
        // setup
        int id = 1;
        String name = "NewEnvName";
        String expectedQuery = "UPDATE viron.environment SET name = '" + name + "' WHERE environment_id = " + id;
        when(dbInteractions.update(expectedQuery)).thenReturn(false);

        // execute
        ResponseEntity<Boolean> response = environmentController.setEnvironmentName(id, name);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        Mockito.verify(dbInteractions).update(expectedQuery);
    }

    @Test
    void testGetEnvironmentOfEntity_SUCCESS() throws SQLException {
        // setup
        int entityId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment WHERE environment_id = (SELECT environment_id FROM viron.entity WHERE entity_id = " + entityId + ")";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("environment_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Env1");
        when(rs.getString("creation_date")).thenReturn("2023-01-01");

        // execute
        ResponseEntity<Environment> response = environmentController.getEnvironmentOfEntity(entityId);

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Env1", response.getBody().getName());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetEnvironmentOfEntity_SQLException() throws SQLException {
        // setup
        int entityId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment WHERE environment_id = (SELECT environment_id FROM viron.entity WHERE entity_id = " + entityId + ")";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Environment> response = environmentController.getEnvironmentOfEntity(entityId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

    @Test
    void testGetEnvironmentOfEntity_EmptyResultSet() throws SQLException {
        // setup
        int entityId = 1;
        ResultSet rs = Mockito.mock(ResultSet.class);
        String expectedQuery = "SELECT * FROM viron.environment WHERE environment_id = (SELECT environment_id FROM viron.entity WHERE entity_id = " + entityId + ")";
        when(dbInteractions.query(expectedQuery)).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // execute
        ResponseEntity<Environment> response = environmentController.getEnvironmentOfEntity(entityId);

        // verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        Mockito.verify(dbInteractions).query(expectedQuery);
    }

}