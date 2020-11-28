package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;

public class TableManager extends ATable {

    private Array<ATable> rowArray;

    public TableManager(float width, float height) {
        setSize(width, height);
        rowArray = new Array<ATable>();
    }

    public void addRow(float widthProportion, float heightProportion, float spaceDownProportion) {
        ATable tmp = new ATable();
        rowArray.add(tmp);
        add(tmp, getWP(heightProportion), getHP(heightProportion))
                .space(
                        0,
                        0,
                        getHP(spaceDownProportion),
                        0
                ).row();
    }

    public <T extends Actor> Cell<Actor> addTo(int rowIndex, Actor actor, float width, float height) {
        return rowArray.get(rowIndex).add(actor, width, height);
    }

    public <T extends Actor> Cell<Actor> addTo(int rowIndex, Actor actor) {
        ATable tmp = rowArray.get(rowIndex);
        return tmp.add(actor, tmp.getWidth(), tmp.getHeight());
    }

    public Cell addTo(int rowIndex, float width, float height) {
        return rowArray.get(rowIndex).add(width, height);
    }

    public Cell addTo(int rowIndex) {
        ATable tmp = rowArray.get(rowIndex);
        return tmp.add(tmp.getWidth(), tmp.getHeight());
    }
}
