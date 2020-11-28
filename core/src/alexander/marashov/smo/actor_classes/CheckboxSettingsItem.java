package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Main;

public class CheckboxSettingsItem extends ATable {

    public CheckboxSettingsItem(float width, float height, String enDescription, String ruDescription,
                         int textSize, Color textColor, InputListener listener, boolean checked) {
        super();
        setBounds(0, 0, width, height);
        ALabel parameterLabel = new ALabel(enDescription, ruDescription, textSize, textColor, Align.left);
        AImageTextButton checkbox = new AImageTextButton("", "", textSize, textColor, listener,
                new SpriteDrawable(new Sprite(Main.assetManager.get(Main.CHECKBOX_UNCHECKED_PATH, Texture.class))),
                new SpriteDrawable(new Sprite(Main.assetManager.get(Main.CHECKBOX_PRESSED_PATH, Texture.class))),
                new SpriteDrawable(new Sprite(Main.assetManager.get(Main.CHECKBOX_CHECKED_PATH, Texture.class)))
        );
        checkbox.setDisabled(false);
        checkbox.setChecked(checked);
        add(checkbox, height, height).space(30).align(Align.left);
        add(parameterLabel, width - height - 60, height).space(30).align(Align.left);
        parameterLabel.setWrap(true);
    }
}
