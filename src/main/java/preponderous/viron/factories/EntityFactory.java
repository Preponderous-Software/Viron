package preponderous.viron.factories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import preponderous.viron.database.DbInteractions;
import preponderous.viron.models.Entity;

@Component
public class EntityFactory {
    private final DbInteractions dbInteractions;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EntityFactory(DbInteractions dbInteractions) {
        this.dbInteractions = dbInteractions;
    }

    public Entity createEntity(String name) throws EntityCreationException {
        logger.info("Attempting to create entity with name: " + name);
        int id = getNextEntityId();
        String creationDate = new java.util.Date().toString();
        String query = "INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (" + id + ", '" + name + "', '" + creationDate + "')";
        boolean success = dbInteractions.update(query);
        if (success) {
            logger.info("Successfully created entity with name: " + name + " and id: " + id + " and creation date: " + creationDate);
            return new Entity(id, name, creationDate);
        }
        throw new EntityCreationException("Error creating entity with name: " + name);
    }

    private int getNextEntityId() {
        ResultSet rs = dbInteractions.query("SELECT nextval('viron.entity_id_seq')");
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error getting next entity id: " + e.getMessage());
        }
        return -1;
    }

    // exception
    public class EntityCreationException extends RuntimeException {
        public EntityCreationException(String message) {
            super(message);
        }
    }
}
