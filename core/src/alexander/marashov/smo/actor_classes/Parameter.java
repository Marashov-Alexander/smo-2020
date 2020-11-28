package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import java.util.function.Consumer;

import alexander.marashov.smo.Callable;

public class Parameter extends Group {

    private static Sprite sliderSprite;
    public static void setSliderSprite(final Sprite sprite) {
        sliderSprite = sprite;
    }

    private final static int TEXT_SIZE = 50;

    final ASlider slider;

    public Parameter(final String name, final int minValue, final int maxValue, final int step, float width, float height, final Consumer<Integer> onChange) {
        setSize(width, height);

        final ALabel title = new ALabel(name, name, TEXT_SIZE, Color.WHITE, Align.right);
        title.setBounds(0, 0, width * 0.5f, height);
        slider = new ASlider(sliderSprite, TEXT_SIZE * 2 / Math.max(1, (int) Math.ceil(Math.log10(maxValue)) - 2), Color.WHITE, Align.center, minValue, maxValue, step,false, onChange);
        slider.setBounds(width * 0.5f, 0f, width * 0.5f, height);

        addActor(title);
        addActor(slider);
    }

    public int getValue() {
        return slider.getValue();
    }
}
