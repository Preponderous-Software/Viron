// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.models;

public class Entity {
    private int entityId;
    private String name;
    private String creationDate;

    public Entity(int entityId, String name, String creationDate) {
        this.entityId = entityId;
        this.name = name;
        this.creationDate = creationDate;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
