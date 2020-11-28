package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;

public class HelpAndExitRow extends ATable {

    private static SpriteDrawable exitIcon;
    private static SpriteDrawable exitIconDown;
    private static SpriteDrawable helpIcon;
    private static SpriteDrawable helpIconDown;

    public static void setBackground(SpriteDrawable exitUp, SpriteDrawable exitDown, SpriteDrawable helpUp, SpriteDrawable helpDown) {
        exitIcon = exitUp;
        exitIconDown = exitDown;
        helpIcon = helpUp;
        helpIconDown = helpDown;
    }

    public HelpAndExitRow(float width, float height,
                          Callable helpAction, Callable exitAction) {
        setSize(width, height);
        AImageButton imageButton;
        add(
                imageButton = new AImageButton(helpIcon, helpIconDown, null, helpAction, false),
                height, height
        ).align(Align.topLeft);
        imageButton.setVisible(false);

        add(width - 2f * height, height);

        add(
                new AImageButton(exitIcon, exitIconDown, null, exitAction, false),
                height, height
        ).align(Align.topRight);
    }
}
