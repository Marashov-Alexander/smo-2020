package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ATextureRegion extends TextureRegion {
    public ATextureRegion(Texture texture, int x, int y, int width, int height) {
        super(texture, x, texture.getHeight() - (y + height), width, height);
    }

    public ATextureRegion(Texture texture) {
        super(texture);
    }

    public ATextureRegion(Texture texture, String data) {
        super(texture);
        String values[] = data.split(" ");
        setRegion(
                (int) Float.valueOf(values[0]).floatValue(),
                (int) Float.valueOf(values[1]).floatValue(),
                (int) Float.valueOf(values[2]).floatValue() - (int) Float.valueOf(values[0]).floatValue(),
                (int) Float.valueOf(values[3]).floatValue() - (int) Float.valueOf(values[1]).floatValue()
        );
    }

    // return two points
    public Rectangle getRegion() {
        return new Rectangle(
                getRegionX(), getRegionY(), getRegionX() + getRegionWidth(), getRegionY() + getRegionHeight()
        );
    }

    @Override
    public void setRegion(int x, int y, int width, int height) {
        super.setRegion(x, getTexture().getHeight() - (y + height), width, height);
    }

    @Override
    public int getRegionY() {
        return getTexture().getHeight() - super.getRegionY() - getRegionHeight();
    }

    @Override
    public void setRegionY(int y) {
        super.setRegionY(getTexture().getHeight() - y - getRegionHeight());
    }
}
