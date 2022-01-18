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
    private final UUID uuid;
    private String name;
    private final LocalDateTime creationDate;
    private final UUID gridUUID;
    private final HashSet<UUID> entities = new HashSet<>();

    public Environment(String name, UUID gridUUID) {
        uuid = UUID.randomUUID();
        this.name = name;
        creationDate = LocalDateTime.now();
        this.gridUUID = gridUUID;
    }

    public abstract Grid getGrid();

    public UUID getUUID() {
        return uuid;
    }

    public UUID getGridUUID() {
        return gridUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void addEntity(Entity entity) {
        entities.add(entity.getUUID());
        entity.setEnvironmentUUID(getUUID());
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getUUID());
    }

    public boolean isEntityPresent(Entity entity) {
        return entities.contains(entity);
    }
}