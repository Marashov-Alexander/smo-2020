package alexander.marashov.smo.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.actor_classes.AImageTextButton;

public class Request extends AImageTextButton {

    private final static int TEXT_SIZE = 40;
    private final static Color TEXT_COLOR = Color.WHITE;

    private final static float OUTLINE = 5;
    private final static Color OUTLINE_COLOR = Color.BLACK;

    public static SpriteDrawable background;
    public static SpriteDrawable background_down;
    public static SpriteDrawable background_checked;

    public static void setBackgrounds(SpriteDrawable up, SpriteDrawable down, SpriteDrawable checked) {
        background = up;
        background_down = down;
        background_checked = checked;
    }

    private final int id;
    private final int priority;
    private final Callable action;

    public Request(int id, int priority, Callable action, float size) {
        super(
                Integer.toString(priority),
                Integer.toString(priority),
                TEXT_SIZE,
                TEXT_COLOR,
                OUTLINE,
                OUTLINE_COLOR,
                action,
                Align.center,
                background,
                background_down,
                background_checked
        );
        setSize(size, size);
        this.id = id;
        this.priority = priority;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public Callable getAction() {
        return action;
    }
}
