// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.models;

public class Location {
    private int locationId;
    private int x;
    private int y;

    public Location(int locationId, int x, int y) {
        this.locationId = locationId;
        this.x = x;
        this.y = y;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
