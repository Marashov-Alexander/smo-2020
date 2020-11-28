package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.MyClickListener;

public class ButtonGroup extends Group {

    private float minX, maxX;
    private float firstWidth;

    public ButtonGroup() {
        addListener(new ActorGestureListener() {
            @Override
            public void pan (InputEvent event, float x, float y, float deltaX, float deltaY) {
                moveBy(deltaX, 0);
                if (getX() < minX) {
                    setX(minX);
                } else if (getX() > maxX) {
                    setX(maxX);
                }
            }
        });
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        minX = x;
        maxX = x;
        firstWidth = width;
    }

    public void addButton(String enText, String ruText, int fontSize, Color fontColor, Callable listenerAction) {
        final AImageTextButton btn = new AImageTextButton(enText, ruText, fontSize, fontColor, new MyClickListener(listenerAction));
        btn.setName("" + (getChildren().size + 1));
        btn.setBounds(0.025f * firstWidth * (1 + getChildren().size) + getChildren().size * 0.3f * firstWidth,
                0.1f * getHeight(), 0.3f * firstWidth, 0.8f * getHeight());

        addActor(btn);

        if (getChildren().size > 3) {
            minX -= 0.3025f * firstWidth;
            setWidth(getWidth() + firstWidth * 0.3025f);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
