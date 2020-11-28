package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

public class ALabelWithIndex extends ATable {
    ALabel label, index;

    public ALabelWithIndex(float width, float height,
                           int textSize, Color color,
                           String enText, String ruText,int index) {
        super();
        label = new ALabel(enText, ruText, textSize, color, Align.center);
        this.index = new ALabel("" + index, "" + index, (int) (textSize / 2f), color, Align.bottomRight);

        setSize(width, height);
        add(label, getWP(1f), getHP(0.8f)).row();
        add(this.index, getWP(1f), getHP(0.2f));
    }

    public void setIndex(int index) {
        this.index.setText("" + index);
    }

    public void setLabel(String text) {
        label.setText(text);
    }
}
