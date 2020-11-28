package alexander.marashov.smo.Windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

import alexander.marashov.smo.Main;
import alexander.marashov.smo.actor_classes.ALabel;

public class TextScrollPane extends ScrollPane {

    private ALabel label;

    public TextScrollPane() {
        super(new ALabel("", "", 60, Color.WHITE, Align.center), Main.scrollPaneStyle);
        label = (ALabel) getActor();
        label.setWrap(true);
    }

    public TextScrollPane(String enText, String ruText) {
        super(new ALabel(enText, ruText, 60, Color.WHITE, Align.center), Main.scrollPaneStyle);
        label = (ALabel) getActor();
        label.setWrap(true);
    }

    public TextScrollPane(String enText, String ruText,int alignment) {
        super(new ALabel(enText, ruText, 60, Color.WHITE, Align.center), Main.scrollPaneStyle);
        label = (ALabel) getActor();
        label.setWrap(true);
        label.setAlignment(alignment);
    }

    public TextScrollPane(String enText, String ruText,int alignment, int textSize) {
        super(new ALabel(enText, ruText, textSize, Color.WHITE, Align.center), Main.scrollPaneStyle);
        label = (ALabel) getActor();
        label.setWrap(true);
        label.setAlignment(alignment);
    }

    public void setText(String enText, String ruText) {
        label.setText(enText, ruText);
    }

    public void setText(String enText, String ruText,boolean wrap) {
        label.setText(enText, ruText);
        label.setWrap(wrap);
    }

    public StringBuilder getText() {
        return label.getText();
    }

    public void addText(String text) {
        label.setText(label.getText().toString() + text);
    }

    public void setLabelSize(float width, float height) {
        label.setSize(width, height);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        // TODO: Override setSize() method to flexible resizing
    }
}
