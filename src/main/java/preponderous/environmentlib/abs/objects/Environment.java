/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environmentlib.abs.objects;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author Daniel McCoy Stephenson
 * @since January 17th, 2022
 */
public abstract class Environment {
    private UUID uuid;
    private String name;
    private LocalDateTime creationDate;
    private UUID gridUUID;
    private HashSet<UUID> entityUUIDs = new HashSet<>();

    public Environment(String name, UUID gridUUID) {
        uuid = UUID.randomUUID();
        this.name = name;
        creationDate = LocalDateTime.now();
        this.gridUUID = gridUUID;
    }

    public UUID getUUID() {
        return uuid;
    }

    public UUID getGridUUID() {
        return gridUUID;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public HashSet<UUID> getEntityUUIDs() {
        return entityUUIDs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEntity(Entity entity) {
        entityUUIDs.add(entity.getUUID());
        entity.setEnvironmentUUID(getUUID());
    }

    public void removeEntity(Entity entity) {
        entityUUIDs.remove(entity.getUUID());
    }

    public boolean isEntityPresent(Entity entity) {
        return entityUUIDs.contains(entity.getUUID());
    }

    public int getNumEntities() {
        return entityUUIDs.size();
    }

    protected void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    protected void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    protected void setGridUUID(UUID gridUUID) {
        this.gridUUID = gridUUID;
    }

    protected void setEntityUUIDs(HashSet<UUID> entityUUIDS) {
        this.entityUUIDs = entityUUIDS;
    }
}