package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.utils.Align;

import alexander.marashov.smo.Callable;

class SwitcherSettingsItem extends ATable {

    SwitcherSettingsItem(float width, float height, String enDescription, String ruDescription,
                         int textSize, Color textColor, Callable listener1, Callable listener2, boolean isFirstEnabled,
                         String enFirstItem, String ruFirstItem, String enSecondItem, String ruSecondItem) {
        super();
        setBounds(0, 0, width, height);
        ALabel parameterLabel = new ALabel(enDescription + ": ", ruDescription + ": ", textSize, textColor, Align.left, 3, Color.WHITE);
        AImageTextButton first = new AImageTextButton(enFirstItem, ruFirstItem, textSize, textColor, 3, Color.WHITE, listener1, Align.center);
        AImageTextButton second = new AImageTextButton(enSecondItem, ruSecondItem, textSize, textColor, 3, Color.WHITE, listener2, Align.center);
        if (isFirstEnabled) {
            first.setChecked(true);
        } else {
            second.setChecked(true);
        }
        first.setDisabled(false);
        second.setDisabled(false);
        new ButtonGroup<>(first, second);
        parameterLabel.setWrap(true);
        add(parameterLabel, width - height * 6f - 60, height).space(30).align(Align.left);
        add(first, height * 3f, height);
        add(second, height * 3f, height);
    }
}
