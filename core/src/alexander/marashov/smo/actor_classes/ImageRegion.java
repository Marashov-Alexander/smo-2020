package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class ImageRegion extends Group {
    private ATextureRegion region;

    private Frame frame;
    private Rectangle currentFrame;
    private boolean needUpdate;

    public ImageRegion(float x, float y, float width, float height,
                       float imgX, float imgY, float imgWidth, float imgHeight,
                       Texture texture, Rectangle frameRect, Texture stickTexture) {
        super();
        needUpdate = false;
        setBounds(x, y, width, height);
        region = new ATextureRegion(texture, (int) imgX, (int) imgY,
                (int) imgWidth, (int) imgHeight);

        addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);

                needUpdate = true;

                int newX = (int) (region.getRegionX() - deltaX);
                int newY = (int) (region.getRegionY() - deltaY);

                if (newX < -(region.getRegionWidth() / 2f - currentFrame.getWidth() / 2)) {
                    newX = (int) -(region.getRegionWidth() / 2f - currentFrame.getWidth() / 2f);
                }

                if (newY < -(region.getRegionHeight() / 2f - currentFrame.getHeight() / 2)) {
                    newY = (int) -(region.getRegionHeight() / 2f - currentFrame.getHeight() / 2f);
                }

                if (newX > (region.getTexture().getWidth() - region.getRegionWidth() / 2f - currentFrame.getWidth() / 2f)) {
                    newX = (int) (region.getTexture().getWidth() - region.getRegionWidth() / 2f - currentFrame.getWidth() / 2f);
                }

                if (newY > (region.getTexture().getHeight() - region.getRegionHeight() / 2f - currentFrame.getHeight() / 2f)) {
                    newY = (int) (region.getTexture().getHeight() - region.getRegionHeight() / 2f - currentFrame.getHeight() / 2f);
                }

                region.setRegion(newX, newY,
                        region.getRegionWidth(), region.getRegionHeight());

                currentFrame.setX1(getImgCenterX() - (frame.getWidth() / 2f));
                currentFrame.setY1(getImgCenterY() - (frame.getHeight() / 2f));

                // zoom is not available
                currentFrame.setWidth(frame.getWidth());
                currentFrame.setHeight(frame.getHeight());
            }
        });
        currentFrame = new Rectangle(0, 0, 0, 0);

        this.frame = new Frame(stickTexture);
        setFrame(frameRect);

        addActor(frame);
    }

    public boolean isNeedUpdate() {
        if (needUpdate) {
            needUpdate = false;
            return true;
        }
        return false;
    }

    public void setFrame(Rectangle rect) {
//        if (rect.getWidth() > 0.6f * getWidth()
//                || rect.getHeight() > 0.6f * getHeight()) {
//            throw new InvalidParameterException("Size of frame must be less than 0.6 of size");
//        }
        this.frame.setRect(rect);

        this.frame.setPosition(
                getCenterX() - frame.getWidth() / 2f,
                getCenterY() - frame.getHeight() / 2f
        );
        currentFrame.setX1((region.getRegionX() + (region.getRegionWidth() / 2f)) - (this.frame.getWidth() / 2f));
        currentFrame.setY1((region.getRegionY() + (region.getRegionHeight() / 2f)) - (this.frame.getHeight() / 2f));

        // zoom is not available
        currentFrame.setWidth(this.frame.getWidth());
        currentFrame.setHeight(this.frame.getHeight());

    }

    public void loadCurrentFrame(Rectangle newCurrentFrame) {
        this.currentFrame = newCurrentFrame;
        region.setRegion(
                (int) (currentFrame.getX() - region.getRegionWidth() / 2f + currentFrame.getWidth() / 2f),
                (int) (currentFrame.getY() - region.getRegionHeight() / 2f + currentFrame.getHeight() / 2f),
                region.getRegionWidth(),
                region.getRegionHeight()
        );
//        Main.addDebugMessage("info", "Region: " + region.getRegionX() + " " + region.getRegionY());
        // ???
    }

//    private float getCenterX() {
//        return getX() + getWidth() / 2f;
//    }
//
//    private float getCenterY() {
//        return getY() + getHeight() / 2f;
//    }

    private float getCenterX() {
        return getWidth() / 2f;
    }

    private float getCenterY() {
        return getHeight() / 2f;
    }

    private float getImgCenterX() {
        return region.getRegionX() + region.getRegionWidth() / 2f;
    }

    private float getImgCenterY() {
        return region.getRegionY() + region.getRegionHeight() / 2f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);

//        frame.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        frame.act(delta);
    }

    public Rectangle getCurrentFrame() {
        return currentFrame;
    }
}
