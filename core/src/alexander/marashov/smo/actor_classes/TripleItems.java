package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import static alexander.marashov.smo.Main.BUTTONS_COLOR;

public class TripleItems extends Group {

    private TextImageButtonGroup[] stars;

    public TripleItems(float buttonSize, float width, float height) {
        setSize(width, height);
        stars = new TextImageButtonGroup[3];
        final float starsDist = (width - buttonSize * 3) / 4f;
        for (int i = 0; i < 3; ++i) {
            stars[i] = new TextImageButtonGroup(null, null, null,
                    null, i * buttonSize + (i + 1) * starsDist, 0, buttonSize, buttonSize,
                    "", "", 40, Color.BLACK, Align.bottomRight, "");
            addActor(stars[i]);
            stars[i].button.setColor(BUTTONS_COLOR);
        }
    }

    public void setData(int index, Drawable drawable, String data) {
        stars[index].setImage(drawable);
        stars[index].label.setText(data);
    }
}
