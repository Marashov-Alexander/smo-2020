package alexander.marashov.smo.Windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Main;
import alexander.marashov.smo.MyClickListener;
import alexander.marashov.smo.actor_classes.AImageButton;
import alexander.marashov.smo.actor_classes.ALabel;
import alexander.marashov.smo.actor_classes.ATable;

import static alexander.marashov.smo.Main.BUTTONS_COLOR;
import static alexander.marashov.smo.Main.screenHeight;
import static alexander.marashov.smo.Main.screenWidth;

public class AWindow extends Group {

    private ATable table;
    public ALabel title;
    public ALabel subtitle;
    private AImageButton btnExit;
    private boolean needExitButton;
    private Image darkBackground;

    private Cell<Actor> contextCell;

    private float hideTime;

    private final ClickListener hideListener = new MyClickListener(() -> addAction(Actions.scaleTo(0, 0, 0.1f)));

    public AWindow(String enTitleString, String ruTitleString, Actor context,
                   float widthProportion, float heightProportion,
                   boolean needExitButton) {
        super();
        init(enTitleString, ruTitleString, context, widthProportion, heightProportion, needExitButton,
                80, 0, 5);
    }

    public AWindow(String enTitleString, String ruTitleString, String enSubtitleString, String ruSubtitleString, Actor context,
                   float widthProportion, float heightProportion, boolean needExitButton,
                   int textSize, int subTextSize, int titleSpaceLeft, int titleSpaceTop, int subtitleSpaceRight, int subtitleSpaceDown) {
        super();
        init(enTitleString, ruTitleString, context, widthProportion, heightProportion, needExitButton,
                textSize, titleSpaceLeft, titleSpaceTop);
        subtitle = new ALabel(enSubtitleString, ruSubtitleString, subTextSize, Color.WHITE, Align.right, 2, Color.BLACK);
        table.row();
        table.add(subtitle, table.getWP(0.3f), table.getHP(0.1f))
                .align(Align.right).space(0, 0, subtitleSpaceDown, subtitleSpaceRight).colspan(2);
    }

    private void init(String enTitleString, String ruTitleString, Actor context,
                      float widthProportion, float heightProportion,
                      boolean needExitButton, int textSize, int titleSpaceLeft, int titleSpaceTop) {
        this.needExitButton = needExitButton;
        setBounds(0, 0, screenWidth, screenHeight);
        darkBackground = new Image(Main.loadingBackground.getDrawable());
        darkBackground.setBounds(0, 0, screenWidth, screenHeight);
        darkBackground.setColor(1, 1, 1, 1f);
        addActor(darkBackground);
        table = new ATable();
//        table.setDebug(true);
        Sprite sprite = new Sprite(Main.assetManager.get(Main.BACKGROUND_PATH, Texture.class));
        sprite.setColor(new Color(0, 0, 0, 0));
        table.setBackground(new SpriteDrawable(sprite));

        table.setBounds(
                0.5f * screenWidth * (1f - widthProportion),
                0.5f * screenHeight * (1f - heightProportion),
                screenWidth * widthProportion,
                screenHeight * heightProportion
        );

        title = new ALabel(enTitleString, ruTitleString, textSize, Color.WHITE, Align.left, 2, Color.BLACK);

        if (needExitButton) {
            btnExit = new AImageButton(
                    new SpriteDrawable(new Sprite(Main.assetManager.get(Main.EXIT_ICON_PATH, Texture.class))),
                    null, null,
                    this::hide, false
            );
            btnExit.setColor(BUTTONS_COLOR);
        } else {
            addListener(new MyClickListener(this::hide));
        }

        table.add(title, table.getWP(0.3f), table.getHP(0.2f)).align(Align.left)
                .space(titleSpaceTop, titleSpaceLeft, 0, 0);

        if (needExitButton)
            table.add(btnExit, table.getHP(0.1f), table.getHP(0.1f))
                    .space(table.getHP(0.01f), 0, table.getHP(0.01f), 0).spaceBottom(0.05f).align(Align.topRight).row();
        else
            table.row();

        if (context != null) {
            context.setSize(table.getWP(0.9f), table.getHP(0.6f));
            contextCell = table.add(context, table.getWP(0.9f), table.getHP(0.6f)).align(Align.center).colspan(2);
        }
        table.row();

        addActor(table);
        setOrigin(Align.center);
        hide(0, 0);

        hideTime = 0;
    }

    public void setBackground(Drawable background) {
        darkBackground.setDrawable(background);
    }

    public void addBtnAction(ClickListener clickListener) {
        if (needExitButton)
            btnExit.addListener(clickListener);
        else
            addListener(clickListener);
    }

    public void hideExitBtn() {
        btnExit.setVisible(false);
    }

    public void hideExitBtn(float time) {
        hideTime = time;
        btnExit.setVisible(false);
    }

    public void showExitBtn() {
        hideTime = 0;
        btnExit.setVisible(true);
    }

    public void clearBtnActions(boolean needHide) {
        if (needExitButton) {
            btnExit.clearListeners();
            if (needHide)
                btnExit.addListener(hideListener);
        } else {
            clearListeners();
            if (needHide)
                addListener(new MyClickListener(this::hide));
        }
    }

    public void setTitle(String enTitleString, String ruTitleString) {
        title.setText(enTitleString, ruTitleString);
    }

    public void hide() {
        hide(0f, 0.25f);
    }

    public void hide(float delay, float duration) {
        isShowed = false;
        setTouchable(Touchable.disabled);
        darkBackground.addAction(Actions.hide());
        if (duration < 0.0001f) {
            setVisible(false);
        }
            addAction(
                    Actions.sequence(
                            Actions.delay(delay),
//                        Actions.scaleTo(1, 1, duration)
                            Actions.alpha(0, duration)
                    )
            );
    }

    private boolean isShowed = false;

    public boolean isShowed() {
        return isShowed;
    }

    public void show() {
        show(0f, 0.25f);
    }

    public void show(float delay, float duration) {
        if (!isVisible()) {
            setVisible(true);
        }
        isShowed = true;
        setTouchable(Touchable.enabled);
        darkBackground.addAction(Actions.show());
        addAction(
                Actions.sequence(
                        Actions.delay(delay),
//                        Actions.scaleTo(1, 1, duration)
                        Actions.alpha(1, duration)
                )
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (hideTime < 0) {
            hideTime = 0;
            showExitBtn();
        } else if (hideTime > 0) {
            hideTime -= delta;
        }
    }

    public void setContext(Actor context) {
        if (context == this.contextCell.getActor()) {
            return;
        }
        context.setSize(table.getWP(0.9f), table.getHP(0.7f));
        contextCell.setActor(context);
    }

    public void setSubtitle(String engStr, String ruStr) {
        subtitle.setText(engStr, ruStr);
    }
}
