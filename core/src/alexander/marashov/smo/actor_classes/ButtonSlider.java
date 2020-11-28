package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import alexander.marashov.smo.Main;

public class ButtonSlider extends ATable {

    private ScrollPane scrollPane;
    private Group buttons;
    private Array<AImageTextButton> buttonArray;

    private float buttonWidth, buttonHeight;
    private float deltaX, deltaY;

    private int currentPage;

    public ButtonSlider(float width, float height) {
        setSize(width, height);
        buttonArray = new Array<>();

        buttonWidth = 3f * getWP(0.8f) / 11f;
        buttonHeight = buttonWidth;
        deltaX = buttonWidth / 6f;
        deltaY = (height - buttonHeight) / 2f;
        currentPage = 0;

        add(new AImageButton(
                new ADrawable(
                        new Sprite(Main.assetManager.get(Main.SLIDER_PATH, Texture.class)),
                        getWP(0.05f), getHP(0.5f),
                        Color.WHITE,
                        true, false
                ),
                new ADrawable(
                        new Sprite(Main.assetManager.get(Main.SLIDER_PATH, Texture.class)),
                        getWP(0.05f), getHP(0.5f),
                        Color.BLUE,
                        true, false
                ),
                () -> {
                    if (currentPage > 0) {
                        currentPage--;
                        scroll();
                    }
                }
        ), getWP(0.1f), getHP(1f));

        add(scrollPane = new ScrollPane(
                buttons = new Group(),
                Main.scrollPaneStyle
        ), getWP(0.8f), getHP(1f));

        scrollPane.setScrollingDisabled(false, true);

        add(new AImageButton(
                new ADrawable(
                        new Sprite(Main.assetManager.get(Main.SLIDER_PATH, Texture.class)),
                        getWP(0.05f), getHP(0.5f),
                        Color.WHITE,
                        false, false
                ),
                new ADrawable(
                        new Sprite(Main.assetManager.get(Main.SLIDER_PATH, Texture.class)),
                        getWP(0.05f), getHP(0.5f),
                        Color.BLUE,
                        false, false
                ),
                () -> {
                    if (!scrollPane.isRightEdge()) {
                        currentPage++;
                        scroll();
                    }
                }
        ), getWP(0.1f), getHP(1f));

        buttons.setBounds(
                0,
                0,
                deltaX,
                buttonHeight + 2 * deltaY
        );

        scrollPane.addCaptureListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                scrollPane.cancel();
            }
        });
    }

    public void addButton(String enText, String ruText, int textSize, Color textColor, EventListener listener) {
        AImageTextButton
                tmp = new AImageTextButton(enText, ruText, textSize, textColor, listener);
        tmp.setBounds(
                deltaX * (1f + buttonArray.size) + buttonWidth * buttonArray.size,
                deltaY,
                buttonWidth,
                buttonHeight
        );

        buttons.setBounds(
                0,
                0,
                buttons.getWidth() + buttonWidth + deltaX,
                buttonHeight + 2 * deltaY
        );

        buttonArray.add(tmp);
        buttons.addActor(tmp);
    }

    private void scroll() {
        scrollPane.scrollTo(
                currentPage * (scrollPane.getWidth() - deltaX),
                0,
                scrollPane.getWidth(),
                scrollPane.getHeight()
        );
    }

    public AImageTextButton getButton(int index) {
        return buttonArray.get(index);
    }
}
