package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.Main;
import alexander.marashov.smo.MyClickListener;

public class AImageTextButton extends ImageTextButton {

    public static SpriteDrawable buttonsBackground;
    public static SpriteDrawable buttonsBackground_down;
    public static SpriteDrawable buttonsBackground_checked;

    public static void setButtonsBackground(SpriteDrawable up, SpriteDrawable down, SpriteDrawable checked) {
        buttonsBackground = up;
        buttonsBackground_down = down;
        buttonsBackground_checked = checked;
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor,
                            Drawable up, Drawable down, Drawable checked) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(up, down, checked, Main.getFont(textSize, textColor)));
        align(Align.left);
        setDisabled(true);
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor, EventListener listener,
                            Drawable up, Drawable down, Drawable checked) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(up, down, checked, Main.getFont(textSize, textColor)));
        align(Align.left);
        setDisabled(true);
        addListener(listener);
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor, Callable listenerAction,
                            Drawable up, Drawable down, Drawable checked) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(up, down, checked, Main.getFont(textSize, textColor)));
        align(Align.left);
        setDisabled(true);
        addListener(new MyClickListener(listenerAction));
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor, float outline, Color outlineColor, Callable listenerAction, int align) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(buttonsBackground, buttonsBackground_down, buttonsBackground_checked,
                Main.getFont(textSize, textColor, outline, outlineColor)));
        align(align);
        setDisabled(true);
        addListener(new MyClickListener(listenerAction));
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor, float outline, Color outlineColor, Callable listenerAction, int align,
                            Drawable up, Drawable down, Drawable checked) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(up, down, checked,
                Main.getFont(textSize, textColor, outline, outlineColor)));
        align(align);
        setDisabled(true);
        addListener(new MyClickListener(listenerAction));
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor, EventListener listener) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(buttonsBackground, buttonsBackground_down, buttonsBackground_checked, Main.getFont(textSize, textColor)));
        align(Align.center);
        setDisabled(true);
        addListener(listener);
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor, Callable listener) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(buttonsBackground, buttonsBackground_down, buttonsBackground_checked, Main.getFont(textSize, textColor)));
        align(Align.center);
        setDisabled(true);
        addListener(new MyClickListener(listener));
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(buttonsBackground, buttonsBackground_down, buttonsBackground_checked, Main.getFont(textSize, textColor)));
        align(Align.center);
        setDisabled(true);
    }

    public AImageTextButton(String enText, String ruText, int textSize, Color textColor, int alignment) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(buttonsBackground, buttonsBackground_down, buttonsBackground_checked, Main.getFont(textSize, textColor)));
        align(alignment);
        setDisabled(true);
    }

    public AImageTextButton(String enText, String ruText, int alignment, ImageTextButtonStyle style) {
        super(Main.isRussian ? ruText : enText, style);
        align(alignment);
        setDisabled(true);
    }

    public AImageTextButton(String enText, String ruText, BitmapFont font,
                            Drawable up, Drawable down, Drawable checked) {
        super(Main.isRussian ? ruText : enText, new ImageTextButtonStyle(up, down, checked, font));
        align(Align.left);
        setDisabled(true);
    }

    public void setText(String enText, String ruText) {
        setText(Main.isRussian ? ruText : enText);
    }
}
