package preponderous.viron.repositories;

import preponderous.viron.models.Location;
import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    List<Location> findAll();
    Optional<Location> findById(int id);
    List<Location> findByEnvironmentId(int environmentId);
    List<Location> findByGridId(int gridId);
    Optional<Location> findByEntityId(int entityId);
    /**
     * Places an entity at a location.
     *
     * @throws org.springframework.dao.DuplicateKeyException if the entity is already placed —
     *         the database, not the caller's prior read, is what decides this, so a request that
     *         lost a race against a concurrent placement lands here rather than returning
     *         {@code false}
     */
    boolean addEntityToLocation(int entityId, int locationId);
    boolean removeEntityFromLocation(int entityId, int locationId);
    boolean removeEntityFromCurrentLocation(int entityId);

    /** Entity ids currently placed at the given location (occupancy / collision query). */
    List<Integer> getEntityIdsAtLocation(int locationId);

    /** Locations in the given grid that currently have no entity placed at them. */
    List<Location> findUnoccupiedByGridId(int gridId);

    /** The grid a location belongs to, if any. */
    Optional<Integer> getGridIdOfLocation(int locationId);

    /** Atomically moves an entity's current placement to the target location. */
    boolean moveEntityToLocation(int entityId, int targetLocationId);
}