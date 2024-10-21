package preponderous.viron.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.factories.EntityFactory;
import preponderous.viron.models.Entity;

@SpringBootTest
public class EntityControllerTest {
    private DbInteractions dbInteractions;
    private EntityFactory entityFactory;
    private EntityController entityController;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        dbInteractions = Mockito.mock(DbInteractions.class);
        entityFactory = Mockito.mock(EntityFactory.class);
        entityController = new EntityController(dbInteractions, entityFactory);
    }
    
    @Test
    void testInitialization() {
        // execute
        EntityController entityController = new EntityController(dbInteractions, entityFactory);

        // verify
        assertNotNull(entityController);
    }

    @Test
    void testGetAllEntities_SUCCESS() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("entity_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Test Entity");
        when(rs.getString("creation_date")).thenReturn("2024-01-01");

        // execute
        ResponseEntity<List<Entity>> response = entityController.getAllEntities();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(dbInteractions).query("SELECT * FROM viron.entity");
    }

    @Test
    void testGetAllEntities_DBInteractionsReturnsNull() {
        // prepare
        when(dbInteractions.query("SELECT * FROM viron.entity")).thenReturn(null);

        // execute
        ResponseEntity<List<Entity>> response = entityController.getAllEntities();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity");
    }

    @Test
    void testGetAllEntities_SQLException() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity")).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<List<Entity>> response = entityController.getAllEntities();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity");
    }

    @Test
    void testGetEntityById_SUCCESS() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id = 1")).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("entity_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Test Entity");
        when(rs.getString("creation_date")).thenReturn("2024-01-01");

        // execute
        ResponseEntity<Entity> response = entityController.getEntityById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getEntityId());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id = 1");
    }

    @Test
    void testGetEntityById_DBInteractionsReturnsNull() {
        // prepare
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id = 1")).thenReturn(null);

        // execute
        ResponseEntity<Entity> response = entityController.getEntityById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id = 1");
    }

    @Test
    void testGetEntityById_SQLException() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id = 1")).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<Entity> response = entityController.getEntityById(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id = 1");
    }

    @Test
    void testCreateEntity_SUCCESS() throws EntityFactory.EntityCreationException {
        // prepare
        Entity entity = new Entity(1, "Test Entity", "2024-01-01");
        when(entityFactory.createEntity("Test Entity")).thenReturn(entity);

        // execute
        ResponseEntity<Entity> response = entityController.createEntity("Test Entity");

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Entity", response.getBody().getName());
        verify(entityFactory).createEntity("Test Entity");
    }

    @Test
    void testCreateEntity_EntityCreationException() throws EntityFactory.EntityCreationException {
        // prepare
        when(entityFactory.createEntity("Test Entity")).thenThrow(EntityFactory.EntityCreationException.class);

        // execute
        ResponseEntity<Entity> response = entityController.createEntity("Test Entity");

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(entityFactory).createEntity("Test Entity");
    }

    @Test
    void testDeleteEntity_SUCCESS() {
        // prepare
        when(dbInteractions.update("DELETE FROM viron.entity WHERE entity_id = 1")).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = entityController.deleteEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(dbInteractions).update("DELETE FROM viron.entity WHERE entity_id = 1");
    }

    @Test
    void testDeleteEntity_FAILURE() {
        // prepare
        when(dbInteractions.update("DELETE FROM viron.entity WHERE entity_id = 1")).thenReturn(false);

        // execute
        ResponseEntity<Boolean> response = entityController.deleteEntity(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(dbInteractions).update("DELETE FROM viron.entity WHERE entity_id = 1");
    }

    @Test
    void testSetEntityName_SUCCESS() {
        // prepare
        when(dbInteractions.update("UPDATE viron.entity SET name = 'New Name' WHERE entity_id = 1")).thenReturn(true);

        // execute
        ResponseEntity<Boolean> response = entityController.setEntityName(1, "New Name");

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(dbInteractions).update("UPDATE viron.entity SET name = 'New Name' WHERE entity_id = 1");
    }

    @Test
    void testSetEntityName_FAILURE() {
        // prepare
        when(dbInteractions.update("UPDATE viron.entity SET name = 'New Name' WHERE entity_id = 1")).thenReturn(false);

        // execute
        ResponseEntity<Boolean> response = entityController.setEntityName(1, "New Name");

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(dbInteractions).update("UPDATE viron.entity SET name = 'New Name' WHERE entity_id = 1");
    }

    @Test
    void testGetEntitiesInEnvironment_SUCCESS() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1)))")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("entity_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Test Entity");
        when(rs.getString("creation_date")).thenReturn("2024-01-01");

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1)))");
    }

    @Test
    void testGetEntitiesInEnvironment_DBInteractionsReturnsNull() {
        // prepare
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1)))")).thenReturn(null);

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1)))");
    }

    @Test
    void testGetEntitiesInEnvironment_SQLException() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1)))")).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInEnvironment(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id in (SELECT grid_id FROM viron.grid_environment WHERE environment_id = 1)))");
    }

    @Test
    void testGetEntitiesInGrid_SUCCESS() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1))")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("entity_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Test Entity");
        when(rs.getString("creation_date")).thenReturn("2024-01-01");

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1))");
    }

    @Test
    void testGetEntitiesInGrid_DBInteractionsReturnsNull() {
        // prepare
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1))")).thenReturn(null);

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1))");
    }

    @Test
    void testGetEntitiesInGrid_SQLException() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1))")).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInGrid(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id in (SELECT location_id FROM viron.location_grid WHERE grid_id = 1))");
    }

    @Test
    void testGetEntitiesInLocation_SUCCESS() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = 1)")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("entity_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Test Entity");
        when(rs.getString("creation_date")).thenReturn("2024-01-01");

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInLocation(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = 1)");
    }

    @Test
    void testGetEntitiesInLocation_DBInteractionsReturnsNull() {
        // prepare
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = 1)")).thenReturn(null);

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInLocation(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = 1)");
    }

    @Test
    void testGetEntitiesInLocation_SQLException() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = 1)")).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesInLocation(1);

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id in (SELECT entity_id FROM viron.entity_location WHERE location_id = 1)");
    }

    @Test
    void testGetEntitiesNotInAnyLocation_SUCCESS() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("entity_id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Test Entity");
        when(rs.getString("creation_date")).thenReturn("2024-01-01");

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesNotInAnyLocation();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)");
    }

    @Test
    void testGetEntitiesNotInAnyLocation_DBInteractionsReturnsNull() {
        // prepare
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)")).thenReturn(null);

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesNotInAnyLocation();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)");
    }

    @Test
    void testGetEntitiesNotInAnyLocation_SQLException() throws SQLException {
        // prepare
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(dbInteractions.query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)")).thenReturn(rs);
        when(rs.next()).thenThrow(new SQLException("Test SQL Exception"));

        // execute
        ResponseEntity<List<Entity>> response = entityController.getEntitiesNotInAnyLocation();

        // verify
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(dbInteractions).query("SELECT * FROM viron.entity WHERE entity_id not in (SELECT entity_id FROM viron.entity_location)");
    }

}
