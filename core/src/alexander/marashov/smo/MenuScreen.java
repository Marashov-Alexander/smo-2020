package alexander.marashov.smo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import alexander.marashov.smo.Windows.AWindow;
import alexander.marashov.smo.Windows.ActorsScrollPane;
import alexander.marashov.smo.Windows.TextScrollPane;
import alexander.marashov.smo.actor_classes.ALabel;
import alexander.marashov.smo.actor_classes.AlignedGroup;
import alexander.marashov.smo.actor_classes.CheckboxSettingsItem;
import alexander.marashov.smo.actor_classes.Parameter;
import alexander.marashov.smo.components.Simulator;
import alexander.marashov.smo.elements.Consumer;
import alexander.marashov.smo.elements.Results;
import alexander.marashov.smo.elements.ScrollableContainer;
import alexander.marashov.smo.elements.Supplier;

import static alexander.marashov.smo.Main.screenHeight;
import static alexander.marashov.smo.Main.screenWidth;

class MenuScreen implements Screen {

    private final Main main;
    private MenuScreenTemplate menuScreenTemplate;
    private Stage stage;

    private AWindow aboutWindow;
    private AWindow resultsWindow;
    private AWindow settingsWindow;

    private ActorsScrollPane resultsScrollPane;

    private ActorsScrollPane settingsScrollPane;

    private Callable afterLoadingCallable;

    private Simulator simulator = null;

    Parameter suppliersCountParam;
    Supplier[] suppliers;
    Parameter consumersCountParam;
    Consumer[] consumers;
    Parameter bufferSizeParam;
    Parameter realisationCountParam;

    private boolean useScript = false;
    private Script script;

    MenuScreen(Main main, Callable callable) {
        this.afterLoadingCallable = callable;
        this.main = main;
        stage = new Stage(new StretchViewport(screenWidth, screenHeight, main.camera)) {
            @Override
            public boolean keyDown(int keyCode) {

                if (!Main.loading && keyCode == Input.Keys.BACK) {
                    if (settingsWindow.isShowed()) {
                        settingsWindow.hide();
                    } else if (aboutWindow.isShowed()) {
                        aboutWindow.hide();
                    } else if (menuScreenTemplate.exitConfirmation.isShowed()) {
                        menuScreenTemplate.exitConfirmation.hide();
                    } else {
                        menuScreenTemplate.exitConfirmation.show();
                    }

                    return true;
                }

                return super.keyDown(keyCode);
            }
        };

        menuScreenTemplate = new MenuScreenTemplate(
                stage,
                "Simulate", "Симуляция",
                () -> {
                    Main.vibrate(50);

                    if (useScript) {
                        script = new Script();
                        final String error = script.parse(Gdx.files.internal("script.txt"));
                        if (error == null) {
                            main.setScreen(new SimulationScreen(main, script));
                        } else {
                            final ALabel errorLabel = new ALabel(error, error, 50, Color.RED, Align.center, 2f, Color.BLACK);
                            errorLabel.setWrap(true);
                            errorLabel.setBounds(0, 0, screenWidth, screenHeight);
                            errorLabel.addAction(Actions.delay(3f, Actions.removeActor()));
                            stage.addActor(errorLabel);
                        }
                    } else {
                        double[] sIntensities = new double[suppliersCountParam.getValue()];
                        for (int i = 0; i < sIntensities.length; ++i) {
                            sIntensities[i] = Double.parseDouble(suppliers[i].getText().toString());
                        }

                        double[] cIntensities = new double[consumersCountParam.getValue()];
                        for (int i = 0; i < cIntensities.length; ++i) {
                            cIntensities[i] = Double.parseDouble(consumers[i].getText().toString());
                        }

                        simulator = new Simulator(
                                bufferSizeParam.getValue(),
                                cIntensities,
                                sIntensities
                        );
                        final int n = realisationCountParam.getValue();
                        simulator.simulateDebug(n);
                        main.setScreen(new SimulationScreen(main, simulator));
                    }
                },
                "Settings", "Настройки", () -> {
            Main.vibrate(50);
            showSettingsWindow();
        },
                "Results", "Результаты", () -> {
            Main.vibrate(50);
            resultsScrollPane.clearActors();
            if (simulator != null) {
                resultsScrollPane.addPlusRow(new Results(screenWidth * 1.2f, screenHeight * 2f, simulator.systemState()));
            } else {
                resultsScrollPane.addPlusRow(new ALabel("", "Проведите симуляцию,\nчтобы получить результаты", 80, Color.WHITE, Align.center, 5f, Color.BLACK));
            }
            resultsWindow.show();
        },
                "About", "О программе", () -> {
            Main.vibrate(50);
            aboutWindow.show();
        },
                () -> Gdx.app.exit(),
                () -> {
                },
                true

        );


    }

    void loadingFinished() {

        settingsWindow = new AWindow(
                "Settings",
                "Настройки",
                "", "",
                settingsScrollPane = new ActorsScrollPane(
                        screenWidth * 0.9f,
                        screenHeight * 0.8f,
                        80, Color.WHITE
                ),
                1f, 1f, true,
                100, 80, 0, 0, 0, 0
        );

        settingsWindow.addBtnAction(new MyClickListener(() -> {
            settingsScrollPane.scrollTo(
                    0,
                    0,
                    settingsScrollPane.getWidth(),
                    settingsScrollPane.getHeight()
            );
        }));

        settingsWindow.addBtnAction(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextScrollPane tsp = new TextScrollPane(

                "AUTHOR:" +
                        "\n- Marashov Alexander",

                "СОЗДАТЕЛЬ:" +
                        "\n- Марашов Александр"
                , Align.left);

        aboutWindow = new AWindow(
                "About SMO", "О программе СМО", "Version from 11.11.2020", "Версия от 11.11.2020",
                tsp,
                1f, 1f, true, 120, 80, 60, 0, 0, 60
        );

        resultsScrollPane = new ActorsScrollPane(screenWidth, screenHeight, 60, Color.WHITE);

        resultsWindow = new AWindow(
                "Simulation results", "Результаты симуляции", "", "",
                resultsScrollPane,
                1f, 1f, true, 60, 60, 60, 0, 0, 60
        );

        stage.addActor(settingsWindow);
        stage.addActor(aboutWindow);
        stage.addActor(resultsWindow);

        ScrollableContainer suppliersContainer = new ScrollableContainer("Интенсивность источников", 0, 0, 10, true, true);
        suppliers = new Supplier[10];
        for (int i = 0; i < 10; ++i) {
            int finalI = i;
            suppliers[i] = new Supplier(1,
                    () -> suppliers[finalI].setText(Math.max((Integer.parseInt(suppliers[finalI].getText().toString()) + 1) % 11, 1) + ""),
                    0.09f * screenWidth * 0.75f, "");
            suppliers[i].setVisible(i + 1 <= 1);
            suppliersContainer.add(suppliers[i]);
        }

        ScrollableContainer consumersContainer = new ScrollableContainer("Интенсивность приборов", 0, 0, 10, true, true);
        consumers = new Consumer[10];
        for (int i = 0; i < 10; ++i) {
            int finalI = i;
            consumers[i] = new Consumer(1,
                    () -> consumers[finalI].setText(Math.max((Integer.parseInt(consumers[finalI].getText().toString()) + 1) % 11, 1) + ""),
                    0.09f * screenWidth * 0.75f, "");
            consumers[i].setVisible(i + 1 <= 1);
            consumersContainer.add(consumers[i]);
        }

        suppliersCountParam = new Parameter("Количество источников", 1, 10, 1, screenWidth * 0.75f, 0.1f * screenHeight,
                (value) -> {
                    for (int i = 0; i < suppliers.length; ++i) {
                        suppliers[i].setVisible(i + 1 <= value);
                    }
                });
        consumersCountParam = new Parameter("Количество приборов", 1, 10, 1, screenWidth * 0.75f, 0.1f * screenHeight,
                (value) -> {
                    for (int i = 0; i < suppliers.length; ++i) {
                        consumers[i].setVisible(i + 1 <= value);
                    }
                }
        );
        bufferSizeParam = new Parameter("Размер буфера", 1, 10, 1, screenWidth * 0.75f, 0.1f * screenHeight,
                (value) -> {}
        );
        realisationCountParam = new Parameter("Начальное\nколичество реализаций", 100, 10000, 100,screenWidth * 0.75f, 0.1f * screenHeight,
                (value) -> {}
        );

        AlignedGroup group = new AlignedGroup(screenWidth * 0.9f, screenHeight * 0.8f, 6, 1);
        group.setActor(suppliersCountParam, 5, 0);
        group.setActor(suppliersContainer.getContainer(), 4, 0);
        group.setActor(consumersCountParam, 3, 0);
        group.setActor(consumersContainer.getContainer(), 2, 0);
        group.setActor(bufferSizeParam, 1, 0);
        group.setActor(realisationCountParam, 0, 0);

        final CheckboxSettingsItem scriptCheckbox = new CheckboxSettingsItem(
                screenWidth * 0.85f, screenHeight * 0.1f,
                "", "Использовать скрипт",
                60, Color.WHITE, new MyClickListener(() -> {
                    useScript = !useScript;
                    group.setVisible(!useScript);
                }),
                useScript
        );

        settingsScrollPane.addPlusRow(scriptCheckbox);
        settingsScrollPane.addPlusRow(group);

        settingsScrollPane.scrollTo(
                0,
                0,
                settingsScrollPane.getWidth(),
                settingsScrollPane.getHeight()
        );

        menuScreenTemplate.showButtons();
    }

    private void showSettingsWindow() {
        settingsWindow.show();
        settingsScrollPane.scrollTo(
                0,
                settingsScrollPane.getMaxY(),
                settingsScrollPane.getWidth(),
                settingsScrollPane.getHeight()
        );
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
        if (Main.loading && Main.assetManager.update()) {
            afterLoadingCallable.call();
            Main.loading = false;
        }
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
