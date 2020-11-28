package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SkewActor extends Actor {
    private ATextureRegion tex;
    private Affine2 affine = new Affine2();
    private float coefficientX, coefficientY;
    private boolean flag;

    public SkewActor(float posX, float posY, float width, float height, ATextureRegion textureRegion, float coefficientX, float coefficientY, boolean flag) {
        super();
        this.tex = textureRegion;
        this.coefficientX = coefficientX;
        this.coefficientY = coefficientY;
        this.flag = flag;
        setBounds(posX, posY, width, height);
    }

    // region must be in normal coordinates!
    public void setRegion(Rectangle region) {
        tex.setRegion((int) region.getX(), (int) region.getY(), (int) region.getWidth(), (int) region.getHeight());
    }

    public Rectangle getRegion() {
        return tex.getRegion();
    }

    public void setCoefficients(float x, float y) {
        coefficientX = x;
        coefficientY = y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        if (flag)
            affine.setToTranslation(getX() + getWidth() / 2, getY() + getHeight() / 2);
        else
            affine.setToTranslation(getX(), getY());

        affine.shear(coefficientX, coefficientY);  // <- modify skew here
        batch.draw(tex, getWidth(), getHeight(), affine);

    }
}
