package alexander.marashov.smo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import alexander.marashov.smo.Windows.AWindow;
import alexander.marashov.smo.Windows.YesNoScrollPane;
import alexander.marashov.smo.actor_classes.AImageTextButton;
import alexander.marashov.smo.components.Simulator;
import alexander.marashov.smo.elements.GraphicSimulator;

import static alexander.marashov.smo.Main.EXIT_ICON_PATH;
import static alexander.marashov.smo.Main.backgroundDrawable;
import static alexander.marashov.smo.Main.screenHeight;
import static alexander.marashov.smo.Main.screenWidth;

class SimulationScreen implements Screen {
    private Main main;
    private Stage stage;

    private AWindow exitConfirmation;
    private AImageTextButton exitButton;
    private GraphicSimulator graphicSimulator;

    private Script script;
    private boolean autoScript;
    private boolean stopScript;
    private boolean busy;

    private Lock lock = new ReentrantLock();

    SimulationScreen(Main main, Script script) {
        initialize(main);
        this.script = script;
        autoScript = true;
        stopScript = false;
        busy = false;
        replaceSimulator(script.next());
    }

    SimulationScreen(Main main, Simulator simulator) {
        initialize(main);
        graphicSimulator = new GraphicSimulator(simulator, 0.1f);
        stage.addActor(graphicSimulator);

        exitButton.toFront();
        exitConfirmation.toFront();

        script = null;
        autoScript = false;
        stopScript = false;
        busy = false;
    }

    private void initialize(Main main) {
        this.main = main;
        stage = new Stage(new StretchViewport(screenWidth, screenHeight, main.camera));
        exitConfirmation = new AWindow(
                "Confirmation",
                "Подтверждение",
                new YesNoScrollPane(
                        "Do you really want to exit?",
                        "Вы действительно хотите выйти?",
                        () -> {
                            try {
                                script.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            main.setScreen(Main.menuScreen);
                        },
                        () -> exitConfirmation.hide()
                ),
                0.75f, 0.75f,
                true
        );

        exitButton = new AImageTextButton(
                "", "", 80, Color.BLACK,
                () -> exitConfirmation.show(),
                new SpriteDrawable(new Sprite(Main.assetManager.get(EXIT_ICON_PATH, Texture.class))),
                null,
                null
        );
        exitButton.setBounds(screenWidth - 120, screenHeight - 120, 120, 120);

        Image background = new Image(backgroundDrawable);
        background.setBounds(0, 0, screenWidth, screenHeight);

        stage.addActor(background);
        stage.addActor(exitButton);
        stage.addActor(exitConfirmation);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        main.readyWhite();
        stage.act();
        stage.draw();

//        if (autoScript) {
//            if (!busy) {
//                if (script.hasNext()) {
//                    Simulator simulator = script.next();
//                    replaceSimulator(simulator);
//                } else {
//                    if (stopScript) {
//                        autoScript = false;
//                    } else {
//                        Simulator simulator = script.next();
//                        replaceSimulator(simulator);
//                        stopScript = true;
//                    }
//                }
//                busy = true;
//            }
//        }

    }

    private void replaceSimulator(Simulator simulator) {
        if (graphicSimulator != null) {
            graphicSimulator.remove();
            graphicSimulator.clear();
        }
        graphicSimulator = new GraphicSimulator(simulator, 0.1f);
        graphicSimulator.enableAutoMode((result -> {
            lock.lock();
            try {
                if (!stopScript)
                    script.write(result);
                if (script.hasNext()) {
                    Simulator sim = script.next();
                    sim.simulateDebug(200);
                    replaceSimulator(sim);
                } else {
                    stopScript = true;
                    autoScript = false;
                    System.out.println("Done");
//                    if (stopScript) {
//                        autoScript = false;
//                        System.out.println("Done");
//                    } else {
//                        stopScript = true;
//                        Simulator sim = script.next();
//                        replaceSimulator(sim);
//                    }
                }
            } finally {
                lock.unlock();
            }
        }));
        stage.addActor(graphicSimulator);
        exitButton.toFront();
        exitConfirmation.toFront();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
