package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Main;

public class AImageLabel extends Group {

    protected Image image;
    protected ALabel label;

    AImageLabel(String enText, String ruText, int textSize, Color color, int align,
                Sprite sprite, boolean isLabelBehindImage) {
        super();
//        label.setSize();
        image = new Image(sprite);
        label = new ALabel(enText, ruText, textSize, color, align);
        if (isLabelBehindImage) {
            addActor(label);
            addActor(image);
        } else {
            addActor(image);
            addActor(label);
        }
    }

    public void setText(String enText, String ruText) {
        label.setText(Main.isRussian ? ruText : enText);
    }

    public String getText() {
        return label.getText().toString();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        image.setSize(width, height);
        label.setSize(width, height);
        image.setOrigin(Align.center);
        label.setOrigin(Align.center);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        image.setSize(width, height);
        label.setSize(width, height);
        image.setOrigin(Align.center);
        label.setOrigin(Align.center);
    }
}
