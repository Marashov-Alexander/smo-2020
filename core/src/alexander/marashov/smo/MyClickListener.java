package alexander.marashov.smo;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyClickListener extends ClickListener {

    private final Callable clickAction;

    public MyClickListener(Callable clickAction) {
        this.clickAction = clickAction;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (!Main.loading && clickAction != null) {
            clickAction.call();
        }
    }
}
