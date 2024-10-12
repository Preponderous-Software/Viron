// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.models;

public class Environment {
    private int environmentId;
    private String name;
    private String creationDate;

    public Environment(int environmentId, String name, String creationDate) {
        this.environmentId = environmentId;
        this.name = name;
        this.creationDate = creationDate;
    }

    public int getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(int entityId) {
        this.environmentId = entityId;
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
        return "Environment{" +
                "environmentId=" + environmentId +
                ", name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
