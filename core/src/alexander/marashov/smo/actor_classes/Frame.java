package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Frame extends Actor {
    private Texture texture;

    private final float lineSize = 5;

    public Frame(Rectangle rectangle, Texture stickTexture) {
        super();
        setBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        this.texture = stickTexture;
    }

    public Frame(Texture stickTexture) {
        super();
        this.texture = stickTexture;
    }

    public void setRect(Rectangle rectangle) {
        setBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), lineSize);
        batch.draw(texture, getX(), getY(), lineSize, getHeight());
        batch.draw(texture, getX() + getWidth(), getY(), lineSize, getHeight());
        batch.draw(texture, getX(), getY() + getHeight(), getWidth(), lineSize);
    }


}
