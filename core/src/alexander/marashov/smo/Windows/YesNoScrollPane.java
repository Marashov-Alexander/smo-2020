package alexander.marashov.smo.Windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.Main;
import alexander.marashov.smo.actor_classes.AImageTextButton;
import alexander.marashov.smo.actor_classes.ATable;


public class YesNoScrollPane extends ScrollPane {

    private ATable table;
    private TextScrollPane textScrollPane;
    public AImageTextButton
            btnYes,
            btnNo;

    private boolean isInitialized;

    public YesNoScrollPane(String enText, String ruText,
                           Callable ifYesAction, Callable ifNoAction) {
        super(new ATable(), Main.scrollPaneStyle);

        textScrollPane = new TextScrollPane(enText, ruText);
        btnYes = new AImageTextButton("Yes", "Да", 80, Color.BLACK, ifYesAction);
        btnNo = new AImageTextButton("No", "Нет", 80, Color.BLACK, ifNoAction);

        btnYes.setColor(Main.BUTTONS_COLOR);
        btnNo.setColor(Main.BUTTONS_COLOR);

        table = (ATable) getActor();

        isInitialized = true;
    }

    public void setText(String enText, String ruText) {
        textScrollPane.setText(enText, ruText);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if (isInitialized) {
            table.setSize(width, height);
            table.clearChildren();
            table.add(textScrollPane, table.getWP(0.9f), table.getHP(0.7f)).colspan(2).row();
            table.add(btnYes, table.getWP(0.3f), table.getHP(0.2f))
                    .align(Align.left)
                    .space(table.getHP(0.05f), table.getWP(0.05f), table.getHP(0.05f), table.getWP(0.05f));
            table.add(btnNo, table.getWP(0.3f), table.getHP(0.2f))
                    .align(Align.right)
                    .space(table.getHP(0.05f), table.getWP(0.05f), table.getHP(0.05f), table.getWP(0.05f));
        }
    }

    public void addYesListener(ClickListener yesListener) {
        btnYes.addListener(yesListener);
    }

    public void addNoListener(ClickListener noListener) {
        btnNo.addListener(noListener);
    }

    public void setYesListener(ClickListener yesListener) {
        btnYes.clearListeners();
        btnYes.addListener(yesListener);
    }

    public void setNoListener(ClickListener noListener) {
        btnNo.clearListeners();
        btnNo.addListener(noListener);
    }
}
