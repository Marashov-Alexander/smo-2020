package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class CelledActor extends Actor {
    private short row, column;

    public void setCellPosition(short row, short column) {
        this.row = row;
        this.column = column;
    }

    public short getRow() {
        return row;
    }

    public short getColumn() {
        return column;
    }
}
