package alexander.marashov.smo.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;
import alexander.marashov.smo.actor_classes.AImageTextButton;

public class BufferCell extends Group {

    private Request request;
    private AImageTextButton cell;

    private final static int TEXT_SIZE = 25;
    private final static Color TEXT_COLOR = Color.BLACK;

    private final static float OUTLINE = 5;
    private final static Color OUTLINE_COLOR = Color.WHITE;

    public static SpriteDrawable cellDrawable;
    public static SpriteDrawable packageCellDrawable;

    public static void setBackgrounds(SpriteDrawable c, SpriteDrawable p) {
        cellDrawable = c;
        packageCellDrawable = p;
    }

    public BufferCell(int index, float size) {
        super();
        setSize(size, size);
        cell = new AImageTextButton(
                Integer.toString(index),
                Integer.toString(index),
                TEXT_SIZE,
                TEXT_COLOR,
                OUTLINE,
                OUTLINE_COLOR,
                null,
                Align.bottomLeft,
                cellDrawable,
                cellDrawable,
                packageCellDrawable
        );
        cell.setTouchable(Touchable.disabled);
        cell.setSize(size, size);
        addActor(cell);
        request = null;
    }

    public void setRequest(final Request request, final boolean isPackage) {
        if (request == null) {
            cell.setChecked(false);
            if (this.request != null) {
                this.request.remove();
            }
            this.request = null;
        } else {
            this.cell.setChecked(isPackage);
            this.request = request;
            request.setBounds(0.1f * getWidth(), 0.1f * getHeight(), getWidth() * 0.8f, getHeight() * 0.8f);
            addActor(request);
            request.setZIndex(0);
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setPackage(boolean isPackage) {
        this.cell.setChecked(isPackage);
    }

    @Override
    public void clear() {
        if (request != null) {
            request.remove();
            System.out.println("Wow");
        }

    }
}
