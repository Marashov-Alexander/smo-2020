package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Main;

public class ALabel extends Label {
    public ALabel(String enText, String ruText, int textSize, Color color, int align) {
        super(Main.isRussian ? ruText : enText, new LabelStyle(Main.getFont(textSize, color), color));
        setAlignment(align);
    }

    public ALabel(String enText, String ruText, int textSize, Color color, int align, float outline, Color colorOutline) {
        super(Main.isRussian ? ruText : enText, new LabelStyle(Main.getFont(textSize + " " + color.toString(), outline, colorOutline), Color.WHITE));
        setAlignment(align);
    }

    public ALabel(String enText, String ruText,int textSize, Color color) {
        super(Main.isRussian ? ruText : enText, new LabelStyle(Main.getFont(textSize, color), color));
        setAlignment(Align.left);
    }

    public ALabel(String enText, String ruText, int textSize, Color color, ClickListener listener) {
        super(Main.isRussian ? ruText : enText, new LabelStyle(Main.getFont(textSize, color), color));
        setAlignment(Align.left);
        addListener(listener);
    }

    public void setText(String enText, String ruText) {
        setText(Main.isRussian ? ruText : enText);
    }

    public String getValue() {
        return super.getText().toString();
    }

    public int getIntValue() {
        return Integer.parseInt(super.getText().toString().replaceFirst(" ", ""));
    }
}
