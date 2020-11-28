package alexander.marashov.smo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Windows.AWindow;
import alexander.marashov.smo.Windows.YesNoScrollPane;
import alexander.marashov.smo.actor_classes.AImageTextButton;
import alexander.marashov.smo.actor_classes.ALabel;
import alexander.marashov.smo.actor_classes.TextImageButtonGroup;

import static alexander.marashov.smo.Main.screenHeight;
import static alexander.marashov.smo.Main.screenWidth;

class MenuScreenTemplate extends Group {

    AWindow exitConfirmation;
    private AImageTextButton button1;
    private AImageTextButton button2;
    private AImageTextButton button3;
    private AImageTextButton button4;
    private TextImageButtonGroup exitButton;
    private final ALabel title;
//    private Image imageLabel;

    MenuScreenTemplate(
            Stage addTo,
            String enTextBtn1, String ruTextBtn1, Callable listener1,
            String enTextBtn2, String ruTextBtn2, Callable listener2,
            String enTextBtn3, String ruTextBtn3, Callable listener3,
            String enTextBtn4, String ruTextBtn4, Callable listener4,
            Callable ifYesAction, Callable helpAction,
            boolean needExitConfirmation
    ) {
        super();
        setBounds(0, 0, screenWidth, screenHeight);

        exitConfirmation = new AWindow(
                "Confirmation",
                "Подтверждение",
                new YesNoScrollPane(
                        "Do you really want to exit?",
                        "Вы действительно хотите выйти?",
                        ifYesAction,
                        () -> exitConfirmation.hide()
                ),
                0.75f, 0.75f,
                true
        );

        addActor(Main.loadingBackground);

        // EXIT BUTTON
        exitButton = new TextImageButtonGroup(
                new SpriteDrawable(new Sprite(Main.assetManager.get(Main.EXIT_ICON_PATH, Texture.class))),
                null, null,
                new MyClickListener(
                        needExitConfirmation
                                ? () -> exitConfirmation.show()
                                : ifYesAction),
                screenWidth - 135 * 1.3f,
                screenHeight - 135 * 1.3f,
                135, 135,
                "", "", 100, Color.BLACK, Align.center, ""
        );
        addActor(exitButton);

        title = new ALabel("The querying system", "Система массового обслуживания", 120, Color.WHITE, Align.center);
        title.setTouchable(Touchable.disabled);
        title.setBounds(0, screenHeight / 1.6f, screenWidth, screenHeight / 3f);
        addActor(title);

        button1 = new AImageTextButton(enTextBtn1, ruTextBtn1, 80, Color.BLACK, 6f, Color.WHITE, listener1, Align.center);
        button1.setColor(Main.BUTTONS_COLOR);
        button1.setBounds(70, 333, 500, 115);
        addActor(button1);

        button3 = new AImageTextButton(enTextBtn3, ruTextBtn3, 80, Color.BLACK, 6f, Color.WHITE, listener3, Align.center);
        button3.setColor(Main.BUTTONS_COLOR);
        button3.setBounds(140, 163, 500, 115);
        addActor(button3);

        button2 = new AImageTextButton(enTextBtn2, ruTextBtn2, 80, Color.BLACK, 6f, Color.WHITE, listener2, Align.center);
        button2.setColor(Main.BUTTONS_COLOR);
        button2.setBounds(screenWidth - 70 - 500, 333, 500, 115);
        addActor(button2);

        button4 = new AImageTextButton(enTextBtn4, ruTextBtn4, 80, Color.BLACK, 6f, Color.WHITE, listener4, Align.center);
        button4.setBounds(screenWidth - 140 - 500, 163, 500, 115);
        button4.setColor(Main.BUTTONS_COLOR);
        addActor(button4);

        title.addAction(Actions.alpha(0));
        button1.addAction(Actions.alpha(0));
        button2.addAction(Actions.alpha(0));
        button3.addAction(Actions.alpha(0));
        button4.addAction(Actions.alpha(0));
        exitButton.addAction(Actions.alpha(0));

        exitButton.button.setColor(Main.BUTTONS_COLOR);

        loadingLabel = new ALabel("LOADING", "загрузка", 240, Color.BLACK, Align.center, 8, Color.WHITE);
        loadingLabel.setBounds(0, 0, screenWidth, screenHeight);
        loadingLabel.setTouchable(Touchable.disabled);

        addTo.addActor(this);
        addTo.addActor(exitConfirmation);

        addActor(loadingLabel);
    }

    public void showExitConfirmation() {
        exitConfirmation.show();
    }

    private ALabel loadingLabel;

    void showButtons() {
        button1.addAction(Actions.alpha(1f, 2f));
        button2.addAction(Actions.alpha(1f, 2f));
        button3.addAction(Actions.alpha(1f, 2f));
        button4.addAction(Actions.alpha(1f, 2f));
        exitButton.addAction(Actions.alpha(1f, 2f));
        title.addAction(Actions.alpha(1f, 2f));
//        imageLabel.addAction(Actions.alpha(1f, 2f));
        loadingLabel.addAction(Actions.alpha(0f, 1f));
    }

}
