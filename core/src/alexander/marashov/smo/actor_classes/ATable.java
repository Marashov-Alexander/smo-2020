package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ATable extends Table {
    public ATable() {super();}

    public float getWP(float proportion) {
        return getWidth() * proportion;
    }

    public float getHP(float proportion) {
        return getHeight() * proportion;
    }

    public <T extends Actor> Cell<Actor> add(Actor actor, float width, float height) {
        actor.setSize(width, height);
        return super.add(actor).minSize(width, height).maxSize(width, height);
    }

    public <T extends Actor> Cell<Actor> add(Actor actor, float minWidth, float minHeight, float scaleFactor) {
        actor.setSize(minWidth, minHeight);
        return super.add(actor).minSize(minWidth, minHeight).maxSize(minWidth * scaleFactor, minHeight * scaleFactor);
    }

    public Cell add(float width, float height) {
        return super.add().minSize(width, height).maxSize(width, height);
    }
}
