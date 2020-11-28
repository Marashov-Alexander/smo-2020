package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.MyClickListener;

public class AImageButton extends ImageButton {

    private Drawable imageUp;

    public static SpriteDrawable buttonsBackground;
    public static SpriteDrawable buttonsBackground_down;
    public static SpriteDrawable buttonsBackground_checked;
    public static void setButtonsBackground(SpriteDrawable up, SpriteDrawable down, SpriteDrawable checked) {
        buttonsBackground = up;
        buttonsBackground_down = down;
        buttonsBackground_checked = checked;
    }

    public AImageButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked, Callable listenerAction) {
        super(new ImageButtonStyle(buttonsBackground, buttonsBackground_down, buttonsBackground_checked, imageUp, imageDown, imageChecked));
        if (listenerAction != null)
            addListener(new MyClickListener(listenerAction));
    }

    public AImageButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked, EventListener listenerAction) {
        super(new ImageButtonStyle(buttonsBackground, buttonsBackground_down, buttonsBackground_checked, imageUp, imageDown, imageChecked));
        if (listenerAction != null)
            addListener(listenerAction);
    }

    public AImageButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked, Callable listenerAction, boolean checkable) {
        super(new ImageButtonStyle(buttonsBackground, buttonsBackground_down, checkable ? buttonsBackground_checked : null, imageUp, imageDown, imageChecked));
        if (listenerAction != null)
            addListener(new MyClickListener(listenerAction));
    }

    public AImageButton(Drawable imageUp, Drawable imageDown, Callable listenerAction) {
        super(imageUp, imageDown);
        if (listenerAction != null)
            addListener(new MyClickListener(listenerAction));
    }

    public AImageButton(ImageButtonStyle style, Callable listenerAction) {
        super(style);
        if (listenerAction != null)
            addListener(new MyClickListener(listenerAction));
    }

    public void setImage(Drawable drawable) {
        ImageButtonStyle style = getStyle();
        style.imageUp = drawable;
        style.imageChecked = drawable;
        style.imageDown = drawable;
        imageUp = style.imageUp;
        setStyle(style);
    }

    public void setCheckedDrawable(Drawable drawable) {
        ImageButtonStyle style = getStyle();
        style.checked = drawable;
        setStyle(style);
    }

    public void setUpDrawable(Drawable drawable) {
        ImageButtonStyle style = getStyle();
        style.up = drawable;
        setStyle(style);
    }

    public Drawable getCheckedDrawable() {
        return getStyle().checked;
    }

    public Drawable getUpDrawable() {
        return getStyle().up;
    }
}
