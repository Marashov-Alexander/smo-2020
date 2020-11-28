package alexander.marashov.smo.actor_classes;

import com.badlogic.gdx.scenes.scene2d.Group;

public class WindowTemplate extends Group {
//    private AlignedGroup group;
//    private ALabel title;
//    private AImageTextButton btnExit;
//    private Actor context;
//    private float hideTime;
//
//    public enum WindowSize {
//        full, threeQuarters, twoThirds, half, third, forth
//    }
//
//    public WindowTemplate() {
//        super();
//        setBounds(0, 0, Main.screenWidth, Main.screenHeight);
//
//        title = new ALabel("", 80, Color.WHITE, Align.left);
//
//        hideTime = 0;
//    }
//
//    public AWindow(String titleString, Actor context,
//                   float widthProportion, float heightProportion,
//                   boolean needExitButton) {
//        super();
//        this.needExitButton = needExitButton;
//        setBounds(0, 0, Main.screenWidth, Main.screenHeight);
//        table = new ATable();
//
//        table.setBounds(
//                0.5f * Main.screenWidth * (1f - widthProportion),
//                0.5f * Main.screenHeight * (1f - heightProportion),
//                Main.screenWidth * widthProportion,
//                Main.screenHeight * heightProportion
//        );
//
//        title = new ALabel(titleString, 80, Color.WHITE, Align.left);
//
//        if (needExitButton) {
//            btnExit = new AImageTextButton("x", 60, Color.WHITE);
//
//
//            btnExit.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    hide();
//                }
//            });
//        } else {
//            addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    hide();
//                }
//            });
//        }
//        table.setBackground("default-window");
//
//        table.add(title, table.getWP(0.3f), table.getHP(0.2f)).align(Align.left)
//                .space(0, 5, 0, 0);
//
//        if (needExitButton)
//            table.add(btnExit, table.getHP(0.1f), table.getHP(0.1f))
//                    .space(table.getHP(0.01f), 0, table.getHP(0.01f), 0).spaceBottom(0.05f).align(Align.topRight).row();
//        else
//            table.row();
//
//        context.setSize(table.getWP(0.9f), table.getHP(0.7f));
//        table.add(context, table.getWP(0.9f), table.getHP(0.7f)).align(Align.center).colspan(2).row();
//
//        addActor(table);
//        setOrigin(Align.center);
//        hide();
//
//        hideTime = 0;
//    }
//
//    public void addBtnAction(ClickListener clickListener) {
//        if (needExitButton)
//            btnExit.addListener(clickListener);
//        else
//            addListener(clickListener);
//    }
//
//    public void hideExitBtn() {
//        btnExit.setVisible(false);
//    }
//
//    public void hideExitBtn(float time) {
//        hideTime = time;
//        btnExit.setVisible(false);
//    }
//
//    public void showExitBtn() {
//        btnExit.setVisible(true);
//    }
//
//    public void clearBtnActions(boolean needHide) {
//        if (needExitButton) {
//            btnExit.clearListeners();
//            if (needHide)
//                btnExit.addListener(new ClickListener() {
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        hide();
//                    }
//                });
//        } else {
//            clearListeners();
//            if (needHide)
//                addListener(new ClickListener() {
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        hide();
//                    }
//                });
//        }
//    }
//
//    public void setTitle(String titleString) {
//        title.setText(titleString);
//    }
//
//    public void hide() {
//        addAction(Actions.scaleTo(0, 0, 0.1f));
//    }
//
//    public void show() {
//        addAction(Actions.scaleTo(1, 1, 0.1f));
//    }
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//        if (hideTime < 0) {
//            hideTime = 0;
//            showExitBtn();
//        } else if (hideTime > 0) {
//            hideTime -= delta;
//        }
//    }
}
