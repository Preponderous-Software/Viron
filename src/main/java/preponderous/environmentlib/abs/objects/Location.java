/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environmentlib.abs.objects;

import java.util.*;

/**
 * @author Daniel McCoy Stephenson
 * @since January 17th, 2022
 */
public abstract class Location {
    private UUID uuid;
    private int x;
    private int y;
    private UUID parentGridUUID;
    private HashSet<UUID> entityUUIDs = new HashSet<>();

    public Location(int x, int y, UUID gridUUID) {
        uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.parentGridUUID = gridUUID;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public UUID getParentGridUUID() {
        return parentGridUUID;
    }

    public HashSet<UUID> getEntityUUIDs() {
        return entityUUIDs;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setParentGridUUID(UUID parentGridUUID) {
        this.parentGridUUID = parentGridUUID;
    }

    public void setEntityUUIDs(HashSet<UUID> entityUUIDs) {
        this.entityUUIDs = entityUUIDs;
    }

    public void addEntity(Entity entity) {
        entityUUIDs.add(entity.getUUID());
        entity.setLocationUUID(getUUID());
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

    public UUID getRandomEntityUUID() {
        UUID[] entitiesArray = entityUUIDs.toArray(new UUID[entityUUIDs.size()]);
        return entitiesArray[new Random().nextInt(entitiesArray.length)];
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    protected void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}