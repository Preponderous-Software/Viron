package preponderous.viron.models;

public class Grid {
    private int gridId;
    private int rows;
    private int columns;

    public Grid(int gridId, int rows, int columns) {
        this.gridId = gridId;
        this.rows = rows;
        this.columns = columns;
    }

    public int getGridId() {
        return gridId;
    }

    public void setGridId(int gridId) {
        this.gridId = gridId;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "gridId=" + gridId +
                ", rows=" + rows +
                ", columns=" + columns +
                '}';
    }
}
