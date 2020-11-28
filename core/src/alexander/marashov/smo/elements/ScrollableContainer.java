package alexander.marashov.smo.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Main;
import alexander.marashov.smo.actor_classes.ALabel;
import alexander.marashov.smo.actor_classes.ARectangle;
import alexander.marashov.smo.actor_classes.Point;


public class ScrollableContainer {

    ARectangle rectangle;
    final Group group;
    final Group actorsGroup;
    final ALabel label;
    ScrollPane scrollPane;
    final boolean isHorizontal;

    float dx, dy;
    int visCount;

    public ScrollableContainer(String title,
                               float x, float y,
                               int visCount,
                               boolean isHorizontal) {
        this.visCount = visCount;
        group = new Group();
        group.setBounds(x, y, 0, 0);

        label = new ALabel(title, title, 40, Color.WHITE, Align.bottomLeft, 2f, Color.BLACK);
        group.addActor(label);

        label.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                group.moveBy(deltaX, deltaY);
            }
        });

        actorsGroup = new Group();
        this.isHorizontal = isHorizontal;
    }

    public ScrollableContainer(String title,
                               float x, float y,
                               int visCount,
                               boolean isHorizontal,
                               boolean fixedPosition
    ) {
        this.visCount = visCount;
        group = new Group();
        group.setBounds(x, y, 0, 0);

        label = new ALabel(title, title, 40, Color.WHITE, Align.bottomLeft, 2f, Color.BLACK);
        group.addActor(label);

        if (!fixedPosition) {
            label.addListener(new ActorGestureListener() {
                @Override
                public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                    group.moveBy(deltaX, deltaY);
                }
            });
        }

        actorsGroup = new Group();
        this.isHorizontal = isHorizontal;
    }


    public void add(final Actor actor) {

        if (actorsGroup.getChildren().size == 0) {
            if (isHorizontal) {

                dx = 0.1f * actor.getWidth();
                dy = 0.1f * actor.getHeight();
                float width = actor.getWidth() * (1.1f * visCount + 0.1f);
                float height = 1.2f * actor.getHeight();

                rectangle = new ARectangle(0, 0, width, height, new Color(1f, 1f, 1.f, 0.3f));
                group.addActor(rectangle);
                rectangle.setZIndex(0);

                setSize(width, height);

                scrollPane = new ScrollPane(actorsGroup, Main.scrollPaneStyle);
                scrollPane.setBounds(0, 0, width, height);
                scrollPane.setScrollingDisabled(false, true);
                group.addActor(scrollPane);
            } else {
                dx = 0.1f * actor.getWidth();
                dy = 0.1f * actor.getHeight();
                float width = 1.2f * actor.getWidth();
                float height = actor.getHeight() * (1.1f * visCount + 0.1f);

                rectangle = new ARectangle(0, 0, width, height, new Color(1f, 1f, 1.f, 0.3f));
                group.addActor(rectangle);
                rectangle.setZIndex(0);

                setSize(width, height);

                scrollPane = new ScrollPane(actorsGroup, Main.scrollPaneStyle);
                scrollPane.setBounds(0, 0, width, height);
                scrollPane.setScrollingDisabled(true, false);
                group.addActor(scrollPane);
            }
        }

        actorsGroup.addActor(actor);
        actor.setPosition(dx, dy);

        if (isHorizontal) {
            dx += 1.1f * actor.getWidth();
            actorsGroup.setWidth(dx);
        } else {
            dy += 1.1f * actor.getHeight();
            actorsGroup.setHeight(dy);
        }
    }

    private final static float headerProportion = 0.2f;
    private void setSize(float width, float height) {
        group.setSize(width, height * (1f + headerProportion));
        actorsGroup.setBounds(0, 0, width, height);
        rectangle.setBounds(0, 0, width, height);
        label.setBounds(0, height, width, headerProportion * height);
    }

    public int scrollTo(final int index) {
        final int normalIndex = index % actorsGroup.getChildren().size;
        Point point;
        if (isHorizontal) {
            point = new Point(normalIndex * dx / actorsGroup.getChildren().size, 0f);
        } else {
            point = new Point(0f, normalIndex * dy / actorsGroup.getChildren().size);
        }
        scrollPane.scrollTo(point.x, point.y, scrollPane.getWidth(), scrollPane.getHeight());
        return normalIndex;
    }

    public Actor getChild(final int index) {
        return actorsGroup.getChild(index);
    }

    public Actor getContainer() {
        return group;
    }

    public void clear() {
        actorsGroup.getChildren().forEach(Actor::clear);
    }
}
