package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class Rotater extends AImageLabel {

    private float
            maxValue,
            minValue,
            value;

    public boolean isLocked;

    public Rotater(Sprite sprite, String enText, String ruText, int textSize, Color color, int align,
                   float minValue, float maxValue) {
        super(enText, ruText, textSize, color, align, sprite, false);
        this.value = minValue;
        isLocked = false;
        this.maxValue = maxValue;
        this.minValue = minValue;

        addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                if (!isLocked) {
                    if (getValue() != maxValue && getValue() != minValue)
                        image.rotateBy(-deltaX);
                    value -= deltaX / 50f;
                }
            }

        });

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (Math.floor(value) > maxValue) {
            value = maxValue;
        } else if (Math.floor(value) < minValue) {
            value = minValue;
        }
        value = Math.max(value, minValue);
        int intValue = getValue();
        String txt =         ((intValue == minValue) ? "  " : ("" + (intValue - 1))) + " " +
                (((intValue == 9) || (intValue == 10)) ? (" " + intValue) : intValue)
                + (intValue == maxValue ? "    " : " " + (intValue + 1));
        setText(
                txt, txt
        );
    }

    public int getValue() {
        return (int) Math.floor(value);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
