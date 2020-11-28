package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

public class AlignedGroup extends Group {

    // ВАЖНО: нумерация строк снизу вверх!
    private GroupRow[] rows;

    public AlignedGroup(float x, float y, float width, float height, GroupRow... rows) {
        setBounds(x, y, width, height);
        this.rows = rows;
    }

    public AlignedGroup(float width, float height, int rowsCount, int columnsCount) {
        setBounds(0, 0, width, height);
        rows = new GroupRow[rowsCount];
        float rowHeightProportion = 1f / rowsCount;
        float columnWidthProportion = 1f / columnsCount;
        float[] array = new float[columnsCount];
        for (int i = 0; i < columnsCount; ++i) {
            array[i] = columnWidthProportion;
        }
        for (int i = 0; i < rowsCount; ++i) {
            rows[i] = new GroupRow(rowHeightProportion, array);
        }
    }

    public AlignedGroup(float width, float height, GroupRow... rows) {
        setBounds(0, 0, width, height);
        this.rows = rows;
    }

    public void setActor(Actor actor, int row, int column) {

        float
                x = 0,
                y = 0;

        for (int i = 0; i < row; i++) {
            y += getHeight() * rows[i].getHeightProportion();
        }

        for (int j = 0; j < column; j++) {
            x += getWidth() * rows[row].getColumnWidthProportion(j);
        }

        actor.setBounds(
                x, y,
                rows[row].getColumnWidthProportion(column) * getWidth(),
                rows[row].getHeightProportion() * getHeight()
        );

        addActor(actor);
    }

    public void setActor(Actor actor, int row, int column, float squareHeightProportion, int align) {
        setActor(actor, row, column, squareHeightProportion * getRowHeight(row) / getColumnWidth(row, column), squareHeightProportion, align);
    }

    public void setActor(Actor actor, int row, int column, float widthProportion, float heightProportion, int align) {

        float
                x = 0,
                y = 0,
                cellWidth = rows[row].getColumnWidthProportion(column) * getWidth(),
                cellHeight = rows[row].getHeightProportion() * getHeight(),
                width = cellWidth * widthProportion,
                height = cellHeight * heightProportion;

        for (int i = 0; i < row; i++) {
            y += getHeight() * rows[i].getHeightProportion();
        }

        for (int j = 0; j < column; j++) {
            x += getWidth() * rows[row].getColumnWidthProportion(j);
        }

        switch (align) {
            case Align.bottom:
                x += (cellWidth - width) / 2f;
                break;
            case Align.bottomLeft:

                break;
            case Align.bottomRight:
                x += cellWidth - width;
                break;
            case Align.center:
                x += (cellWidth - width) / 2f;
                y += (cellHeight - height) / 2f;
                break;
            case Align.left:
                y += (cellHeight - height) / 2f;
                break;
            case Align.right:
                x += cellWidth - width;
                y += (cellHeight - height) / 2f;
                break;
            case Align.top:
                x += (cellWidth - width) / 2f;
                y += cellHeight - height;
                break;
            case Align.topLeft:
                y += cellHeight - height;
                break;
            case Align.topRight:
                x += cellWidth - width;
                y += cellHeight - height;
                break;
        }

        actor.setBounds(
                x, y,
                width, height
        );
        addActor(actor);
    }

    public void setActor(Actor actor, int row, int column, float widthProportion, float heightProportion, int align, float spaceProportion) {

        float
                x = 0,
                y = 0,
                cellWidth = rows[row].getColumnWidthProportion(column) * getWidth(),
                cellHeight = rows[row].getHeightProportion() * getHeight(),
                width = cellWidth * widthProportion,
                height = cellHeight * heightProportion;

        for (int i = 0; i < row; i++) {
            y += getHeight() * rows[i].getHeightProportion();
        }

        for (int j = 0; j < column; j++) {
            x += getWidth() * rows[row].getColumnWidthProportion(j);
        }

        switch (align) {
            case Align.bottom:
                x += (cellWidth - width) / 2f;
                y += cellHeight * spaceProportion;
                break;
            case Align.bottomLeft:
                y += cellHeight * spaceProportion;
                x += cellWidth * spaceProportion;
                break;
            case Align.bottomRight:
                x += cellWidth - width;
                y += cellHeight * spaceProportion;
                x -= cellWidth * spaceProportion;
                break;
            case Align.center:
                x += (cellWidth - width) / 2f;
                y += (cellHeight - height) / 2f;
                break;
            case Align.left:
                y += (cellHeight - height) / 2f;
                x += cellWidth * spaceProportion;
                break;
            case Align.right:
                x += cellWidth - width;
                y += (cellHeight - height) / 2f;
                x -= cellWidth * spaceProportion;
                break;
            case Align.top:
                x += (cellWidth - width) / 2f;
                y += cellHeight - height;
                y -= cellHeight * spaceProportion;
                break;
            case Align.topLeft:
                y += cellHeight - height;
                y -= cellHeight * spaceProportion;
                x += cellWidth * spaceProportion;
                break;
            case Align.topRight:
                x += cellWidth - width;
                y += cellHeight - height;
                y -= cellHeight * spaceProportion;
                x -= cellWidth * spaceProportion;
                break;
        }

        actor.setBounds(
                x, y,
                width, height
        );
        addActor(actor);
    }

    public void setActor(Actor actor, int firstRow, int firstColumn, int secondRow, int secondColumn) {

        float
                firstX = 0,
                firstY = 0;
        for (int i = 0; i < firstRow; i++) {
            firstY += getHeight() * rows[i].getHeightProportion();
        }
        for (int j = 0; j < firstColumn; j++) {
            firstX += getWidth() * rows[firstRow].getColumnWidthProportion(j);
        }

        float
                secondX = firstX,
                secondY = firstY;
        for (int i = firstRow; i < secondRow; i++) {
            secondY += getHeight() * rows[i].getHeightProportion();
        }
        for (int j = firstColumn; j < secondColumn; j++) {
            secondX += getWidth() * rows[secondRow].getColumnWidthProportion(j);
        }

        actor.setBounds(
                firstX, firstY,
                secondX - firstX + rows[secondRow].getColumnWidthProportion(secondColumn) * getWidth(),
                secondY - firstY + rows[secondRow].getHeightProportion() * getHeight()
        );

        addActor(actor);
    }

    public void setActor(Actor actor, int firstRow, int firstColumn, int secondRow, int secondColumn, float widthProportion, float heightProportion) {
        setActor(actor, firstRow, firstColumn, secondRow, secondColumn);
        actor.setOrigin(Align.center);
        actor.setScale(widthProportion, heightProportion);
    }

    public float getRowHeight(int row) {
        return rows[row].getHeightProportion() * getHeight();
    }

    public float getColumnWidth(int row, int column) {
        return rows[row].getColumnWidthProportion(column) * getWidth();
    }
}
