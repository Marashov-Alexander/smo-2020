package alexander.marashov.smo.Windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import alexander.marashov.smo.Main;
import alexander.marashov.smo.actor_classes.AImageTextButton;
import alexander.marashov.smo.actor_classes.ATable;

public class ActorsScrollPane extends ScrollPane {

    protected ATable table;
    protected int textSize;
    protected Color textColor;
    private boolean isInitialized;

    public ActorsScrollPane(float width, float height, int textSize, Color textColor) {
        super(new ATable(), Main.scrollPaneStyle);
        table = (ATable) getActor();
        table.setSize(width, height);
        this.textSize = textSize;
        this.textColor = textColor;
        isInitialized = true;
    }

    public void addButton(String enText, String ruText, ClickListener listener) {
        table.add(
                new AImageTextButton(
                        enText, ruText, textSize, textColor, listener
                ),
                table.getWP(0.8f), table.getHP(0.2f)
        ).spaceBottom(table.getHP(0.05f)).row();
    }

    public void addPlusRow(Actor actor) {
        table.add(
                actor
        ).spaceBottom(table.getHP(0.1f)).row();
    }

    public void addPlusRow(Actor actor,
                           float widthProportion, float heightProportion,
                           float spaceBottomProportion, int align) {
        actor.setSize(table.getWP(widthProportion),
                table.getHP(heightProportion));
        table.add(
                actor,
                table.getWP(widthProportion),
                table.getHP(heightProportion)
        ).spaceBottom(table.getHP(spaceBottomProportion)).align(align).row();
    }

    public void addPlusRow(Actor actor,
                           float widthProportion, float heightProportion,
                           float spaceBottomProportion, int align, int colspan) {
        actor.setSize(table.getWP(widthProportion),
                table.getHP(heightProportion));
        table.add(
                actor,
                table.getWP(widthProportion),
                table.getHP(heightProportion)
        ).spaceBottom(table.getHP(spaceBottomProportion)).colspan(colspan).align(align).row();
    }

    public void addPlusRow(float width, float height,
                           Actor actor,
                           float spaceBottom, int align) {
        actor.setSize(width,
                height);
        table.add(
                actor,
                width,
                height
        ).spaceBottom(spaceBottom).align(align).row();
    }

    public void add(Actor actor, float widthProportion, float heightProportion, float spaceBottomProportion) {
        table.add(
                actor,
                table.getWP(widthProportion),
                table.getHP(heightProportion)
        ).spaceBottom(table.getHP(spaceBottomProportion));
    }

    public void row() {
        table.row();
    }

    public void clearActors() {
        table.clearChildren();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if (isInitialized)
        {
            table.setSize(width, height);
        }
    }
}
