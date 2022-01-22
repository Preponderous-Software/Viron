/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environmentlib.abs.objects;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel McCoy Stephenson
 * @since January 17th, 2022
 */
public abstract class Entity {
    private UUID uuid;
    private String name;
    private LocalDateTime creationDate;
    private UUID environmentUUID;
    private UUID locationUUID;

    public Entity(String name) {
        uuid = UUID.randomUUID();
        this.name = name;
        creationDate = LocalDateTime.now();
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public UUID getEnvironmentUUID() {
        return environmentUUID;
    }

    public UUID getLocationUUID() {
        return locationUUID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnvironmentUUID(UUID environmentUUID) {
        this.environmentUUID = environmentUUID;
    }

    public void setLocationUUID(UUID locationUUD) {
        this.locationUUID = locationUUD;
    }

    protected void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    protected void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}