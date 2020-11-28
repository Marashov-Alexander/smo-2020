package alexander.marashov.smo.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.actor_classes.AImageTextButton;

public class Consumer extends AImageTextButton {

    private final static int TEXT_SIZE = 35;
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

    public Consumer(int priority, Callable action, float width) {
        super(
                "          " + "Consumer " + priority,
                "          " + "Прибор " + priority,
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
        setSize(width, width * 0.46f);
    }

    public Consumer(int index, Callable action, float width, String text) {
        super(
                text + index,
                text + index,
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
        setSize(width, width * 0.46f);
    }
}
