package alexander.marashov.smo.actor_classes;

public class GroupRow {

    private float heightProportion;
    private float[] columnWidthProportions;

    public GroupRow(float heightProportion, float... columnWidthProportions) {
        this.heightProportion = heightProportion;
        this.columnWidthProportions = columnWidthProportions;
    }

    public float getHeightProportion() {
        return heightProportion;
    }

    public float getColumnWidthProportion(int index) {
        if (index < 0 || index >= columnWidthProportions.length)
            throw new IndexOutOfBoundsException();
        return columnWidthProportions[index];
    }

    public int getColumnsCount() {
        return columnWidthProportions.length;
    }
}
