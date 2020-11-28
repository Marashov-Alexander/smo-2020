package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import java.util.function.Consumer;

import alexander.marashov.smo.Callable;

public class ASlider extends Group {

    private AImageLabel foreground;
//    private Image background;

    private float
            maxValue,
            minValue,
            value;

    private int step;

    public boolean isLocked;
    private boolean changed;

    private final Consumer<Integer> onChange;

    public ASlider (Sprite sprite, int textSize, Color color, int align,
                    float minValue, float maxValue, int step, final boolean isVertical, final Consumer<Integer> onChange) {
        this.onChange = onChange;
        this.foreground = new AImageLabel("", "", textSize, color, align, sprite, true);
//        this.background = new Image(background);
        this.value = minValue;
        this.step = step;
        this.isLocked = false;
        this.maxValue = maxValue;
        this.minValue = minValue;
        sprite.setColor(Color.ORANGE);
        changed = false;

        addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                if (!isLocked) {
                    changed = true;
                    if (isVertical)
                        value -= step * deltaY / 50f;
                    else
                        value -= step * deltaX / 50f;
                }
            }

        });

//        addActor(this.background);
        addActor(this.foreground);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
//        this.background.setBounds(x, y, width, height);

        this.foreground.setSize(width, height);
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
        String txt = ((intValue == minValue) ? "  " : ("" + (intValue - 1))) + " " +
                (((intValue == 9) || (intValue == 10)) ? (" " + intValue) : intValue)
                + (intValue == maxValue ? "    " : " " + (intValue + 1));
        foreground.setText(
                txt, txt
        );
        if (changed) {
            changed = false;
            onChange.accept(intValue);
        }
//            if (Math.abs(speed) > 0.01) {
//                value += speed * Gdx.graphics.getDeltaTime() / 100;
//                if (Math.floor(value) > maxValue) {
//                    value = maxValue;
//                } else if (Math.floor(value) < minValue) {
//                    value = minValue;
//                }
//                speed -= speed * Gdx.graphics.getDeltaTime();
//                setText("" + Math.floor(value));
//            }
    }

    public int getValue() {
        return (int) Math.floor(value);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
