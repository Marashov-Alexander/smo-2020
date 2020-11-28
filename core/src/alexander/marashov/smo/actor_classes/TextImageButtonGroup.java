package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class TextImageButtonGroup extends AImageButton {
    public AImageButton button;
    public ALabel label;
    public Drawable imageUp;
    private float w, h;

    public TextImageButtonGroup(Drawable imageUp, Drawable imageDown, Drawable imageChecked, EventListener listenerAction,
                                float x, float y, float width, float height,
                                String enText, String ruText, int textSize, Color color, int align, String name
                         ) {
        super(imageUp, imageDown, imageChecked, listenerAction);
        this.imageUp = imageUp;
        setName(name);
        button = this;
        button.setBounds(x, y, width, height);
        button.setDisabled(true);
        label = new ALabel(enText, ruText, textSize, color, align, 2, Color.WHITE);
        label.setBounds(0, 0, width, height);
        label.setTouchable(Touchable.disabled);
        addActor(label);
        w = width;
        h = height;
    }

    public void setImage(Drawable drawable) {
        ImageButtonStyle style = button.getStyle();
        style.imageUp = drawable;
        style.imageDown = drawable;
        style.imageChecked = drawable;
        imageUp = style.imageUp;
        button.setStyle(style);
        getImage().setSize(getWidth(), getHeight());
        getImage().setOrigin(Align.center);
    }

    public void setImage(Sprite sprite, Color color) {
        setImage(new ADrawable(sprite, color));
    }
}
