package alexander.marashov.smo.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.actor_classes.AImageTextButton;

public class Arrow extends AImageTextButton {

    private final static int TEXT_SIZE = 40;
    private final static Color TEXT_COLOR = Color.WHITE;

    private final static float OUTLINE = 5;
    private final static Color OUTLINE_COLOR = Color.BLACK;

    public static SpriteDrawable horizontalArrow;
    public static SpriteDrawable verticalArrow;
    public static void setBackgrounds(SpriteDrawable horizontal, SpriteDrawable vertical) {
        horizontalArrow = horizontal;
        verticalArrow = vertical;
    }

    public Arrow(boolean horizontal, float size) {
        super(
                "",
                "",
                TEXT_SIZE,
                TEXT_COLOR,
                OUTLINE,
                OUTLINE_COLOR,
                null,
                Align.center,
                horizontal ? horizontalArrow : verticalArrow,
                horizontal ? horizontalArrow : verticalArrow,
                horizontal ? horizontalArrow : verticalArrow
        );
        setSize(size, size);
    }
}
