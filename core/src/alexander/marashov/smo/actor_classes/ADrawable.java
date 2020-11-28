package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class ADrawable extends SpriteDrawable {

    public ADrawable(Sprite sprite, float width, float height) {
        super();
        sprite.setSize(width, height);
        setSprite(sprite);
    }

    public ADrawable(Sprite sprite, float width, float height, boolean flipX, boolean flipY) {
        super();
        sprite.setSize(width, height);
        setSprite(sprite);
        getSprite().setFlip(flipX, flipY);
    }

    public ADrawable(Sprite sprite, float width, float height, Color color, boolean flipX, boolean flipY) {
        super();
        sprite.setSize(width, height);
        setSprite(sprite);
        getSprite().setFlip(flipX, flipY);
        getSprite().setColor(color);
    }

    public ADrawable(Sprite sprite, Color color) {
        super();
        setSprite(sprite);
        getSprite().setColor(color);
    }

    public ADrawable(Sprite sprite, boolean flipX, boolean flipY) {
        super();
        setSprite(sprite);
        getSprite().setFlip(flipX, flipY);
    }

    public ADrawable(Sprite sprite, float width, float height, Color color) {
        sprite.setSize(width, height);
        setSprite(sprite);
        getSprite().setColor(color);
    }

    public void setSize(float width, float height) {
        Sprite sprite = getSprite();
        sprite.setSize(width, height);
        setSprite(sprite);
    }

}
