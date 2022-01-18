/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environment.abs.objects;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel McCoy Stephenson
 * @since January 7th, 2022
 */
public abstract class Entity {
    private final UUID uuid;
    private String name;
    private final LocalDateTime creationDate;
    private UUID environmentUUID;
    private UUID locationUUID;

    public Entity(String name) {
        uuid = UUID.randomUUID();
        this.name = name;
        creationDate = LocalDateTime.now();
    }

    public abstract Environment getEnvironment();
    public abstract Location getLocation();

    public UUID getUUID() {
        return uuid;
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

    public UUID getEnvironmentUUID() {
        return environmentUUID;
    }

    public void setEnvironmentUUID(UUID environmentUUID) {
        this.environmentUUID = environmentUUID;
    }

    private UUID getLocationUUID() {
        return locationUUID;
    }

    public void setLocationUUID(UUID locationUUD) {
        this.locationUUID = locationUUD;
    }
}