package alexander.marashov.smo.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

import java.util.concurrent.atomic.AtomicBoolean;

import alexander.marashov.smo.Windows.AWindow;
import alexander.marashov.smo.Windows.TextScrollPane;
import alexander.marashov.smo.actor_classes.ALabel;
import alexander.marashov.smo.components.Simulator;

import static alexander.marashov.smo.Main.screenHeight;
import static alexander.marashov.smo.Main.screenWidth;

public class GraphicSimulator extends Group {

    final AWindow infoWindow;
    final TextScrollPane tsp;

    final ScrollableContainer controlContainer;
    final ScrollableContainer progressContainer;

    final ALabel progressLabel;

    final float supplierSize = 300f;
    final ScrollableContainer suppliersContainer;
    final Supplier[] suppliers;

    final float consumerSize = 300f;
    final ScrollableContainer consumersContainer;
    final Consumer[] consumers;

    final float bufferSize = 600f;
    final float cellSize = 100f;
    final ScrollableContainer bufferContainer;
    final BufferCell[] bufferCells;

    final float memorySize = 150f;
    final ScrollableContainer memoryManagerContainer;
    final MemoryManager memoryManager;

    final float consumerManagerSize = 150f;
    final ScrollableContainer consumerManagerContainer;
    final ConsumerManager consumerManager;

    final float trashBinSize = 150f;
    final ScrollableContainer trashBinContainer;
    final TrashBin trashBin;

    final Request tmpRequest;

    final float speed;

    final Simulator simulator;

    float delta = 0f;
    private final AtomicBoolean autoEnabled = new AtomicBoolean(false);
    private final AtomicBoolean skipEnabled = new AtomicBoolean(false);

    private final AtomicBoolean autoMode = new AtomicBoolean(false);
    private final AtomicBoolean simulationOver = new AtomicBoolean(false);
    private float tmp = 0f;

    private ALabel label;

    private java.util.function.Consumer<Simulator.SimulationResult> resultConsumer;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        tmp += Gdx.graphics.getDeltaTime();

        if (autoMode.get() && tmp > 0.1f) {
            tmp = 0f;
            if (simulationOver.get()) {
                resultConsumer.accept(simulator.systemState());
            } else {
                if (!skipEnabled.get()) {
                    skipEnabled.set(true);
                    simulator.simulate();
                    simulationIsOver();
                }
            }
        }

        if (autoEnabled.get() && !skipEnabled.get()) {
            delta += Gdx.graphics.getDeltaTime();
            if (delta >= 1f + speed * 3f) {
                delta = 0f;
                drawNextEvent();
            }
        }
    }

    public void enableAutoMode(
            final java.util.function.Consumer<Simulator.SimulationResult> resultConsumer
    ) {
        autoMode.set(true);
        this.resultConsumer = resultConsumer;
    }

    public GraphicSimulator(final Simulator simulator, final float speed) {
        label = new ALabel("", "", 100, Color.WHITE, Align.center, 6f, Color.BLACK);
        tsp = new TextScrollPane("", "", Align.center);
        this.infoWindow = new AWindow(
                "Information", "Информация",
                tsp,
                1f, 1f, false
        );
        this.simulator = simulator;
        setSize(screenWidth, screenHeight);
        this.speed = speed;
        controlContainer = new ScrollableContainer(
                "Панель управления",
                0.25f * screenWidth, 0.76f * screenHeight,
                4, true
        );
        controlContainer.add(new NextButton(this::drawNextEvent, 0.15f * screenHeight));
        controlContainer.add(new AutoButton(() -> autoEnabled.set(!autoEnabled.get()), 0.15f * screenHeight));
        controlContainer.add(new SkipButton(() -> {
            skipEnabled.set(true);
            simulator.simulate();
            simulationIsOver();

        }, 0.15f * screenHeight));

        progressContainer = new ScrollableContainer(
                "Прогресс",
                0f, 0.76f * screenHeight,
                1, false
        );
        final String progressString = simulator.getCounter() + " / " + simulator.getN();
        progressLabel = new ALabel(progressString, progressString, 60, Color.BLACK, Align.left, 5f, Color.WHITE);
        progressLabel.setSize(0.2f * screenWidth, 0.1f * screenHeight);
        progressContainer.add(progressLabel);

        suppliersContainer = new ScrollableContainer(
                "Источники",
                0, 0,
                5, false
        );
        suppliers = new Supplier[simulator.getSuppliersCount()];
        for (int i = 0; i < suppliers.length; ++i) {
            int finalI = i;
            suppliers[i] = new Supplier(i, () -> {
                infoWindow.setTitle("Источник " + finalI, "Источник " + finalI);
                tsp.setText(simulator.supplierInfo(finalI), simulator.supplierInfo(finalI));
                infoWindow.show();
            }, supplierSize);
            suppliersContainer.add(suppliers[i]);
        }

        bufferContainer = new ScrollableContainer(
                "Буфер памяти",
                (screenWidth - bufferSize) * 0.5f, 0.575f * screenHeight,
                5, true
        );
        bufferCells = new BufferCell[simulator.getBufferSize()];
        for (int i = 0; i < bufferCells.length; ++i) {
            BufferCell cell = new BufferCell(i, cellSize);
            bufferCells[i] = cell;
            bufferContainer.add(cell);
        }

        memoryManagerContainer = new ScrollableContainer(
                "Диспетчер памяти",
                (screenWidth - 2 * bufferSize) * 0.5f, 0.3f * screenHeight,
                1, false
        );
        memoryManager = new MemoryManager("", () -> {
            infoWindow.setTitle("Диспетчер памяти", "Диспетчер памяти");
            tsp.setText(simulator.memManagerInfo(), simulator.memManagerInfo());
            infoWindow.show();
        }, memorySize);
        memoryManagerContainer.add(memoryManager);

        consumerManagerContainer = new ScrollableContainer(
                "Диспетчер выбора",
                (screenWidth + bufferSize) * 0.5f, 0.25f * screenHeight,
                1, true
        );
        consumerManager = new ConsumerManager("", () -> {
            infoWindow.setTitle("Диспетчер выбора", "Диспетчер выбора");
            tsp.setText(simulator.chooseManagerInfo(), simulator.chooseManagerInfo());
            infoWindow.show();
        }, consumerManagerSize);
        consumerManagerContainer.add(consumerManager);

        consumersContainer = new ScrollableContainer(
                "Приборы",
                screenWidth - consumerSize * 1.2f, 0,
                5, false
        );
        consumers = new Consumer[simulator.getConsumersCount()];
        for (int i = 0; i < consumers.length; ++i) {
            int finalI = i;
            consumers[i] = new Consumer(i, () -> {
                infoWindow.setTitle("Прибор " + finalI, "Прибор " + finalI);
                tsp.setText(simulator.consumerInfo(finalI), simulator.consumerInfo(finalI));
                infoWindow.show();
            }, consumerSize);
            consumersContainer.add(consumers[i]);
        }

        trashBinContainer = new ScrollableContainer(
                "Отказ",
                consumerSize * 1.2f + screenWidth * 0.2f, 0,
                1, true
        );
        trashBin = new TrashBin(() -> {
            infoWindow.setTitle("Отказ", "Отказ");
            tsp.setText(simulator.rejectInfo(), simulator.rejectInfo());
            infoWindow.show();
        }, trashBinSize);
        trashBinContainer.add(trashBin);


        ScrollableContainer tmpContainer = new ScrollableContainer(
                "Принятые",
                consumerSize * 1.2f + screenWidth * 0.2f, 0,
                1, true
        );
        tmpRequest = new Request(0, 0, null, cellSize);
        tmpContainer.add(tmpRequest);
        tmpContainer.getContainer().setPosition(screenWidth - consumersContainer.getContainer().getWidth(), screenHeight);

        addActor(tmpContainer.getContainer());
        addActor(consumerManagerContainer.getContainer());
        addActor(memoryManagerContainer.getContainer());
        addActor(trashBinContainer.getContainer());
        addActor(suppliersContainer.getContainer());
        addActor(bufferContainer.getContainer());
        addActor(consumersContainer.getContainer());
        addActor(controlContainer.getContainer());
        addActor(progressContainer.getContainer());
        addActor(label);
        addActor(infoWindow);

    }

    private void simulationIsOver() {
        autoEnabled.set(false);
        skipEnabled.set(false);

        if (simulator.hasEnoughPrecision()) {
            simulationOver.set(true);
            controlContainer.getContainer().setVisible(false);
            label.setText("Simulation is over", "Симуляция завершена");
            label.setTouchable(Touchable.disabled);
            label.setBounds(0, 0, screenWidth, screenHeight);
            label.addAction(
                    Actions.sequence(
                            Actions.alpha(1f, 1f),
                            Actions.delay(2f),
                            Actions.alpha(0f, 1f)
                    )
            );
        } else {
            label.setText("Not enough precision", "Недостаточная точность.\nНовое значение итераций = " + simulator.getN());
            label.setTouchable(Touchable.disabled);
            label.setBounds(0, 0, screenWidth, screenHeight);
            label.addAction(
                    Actions.sequence(
                            Actions.alpha(1f, 1f),
                            Actions.delay(2f),
                            Actions.alpha(0f, 1f)
                    )
            );
            progressLabel.setText(simulator.getCounter() + " / " + simulator.getN());
            bufferContainer.clear();
        }
    }

    private void drawNextEvent() {
        if (skipEnabled.get()) {
            return;
        }
        GraphicEvent event = getNextEvent();
        if (event == null) {
            return;
        }
        switch (event.eventType) {
            case fromSupplierToMemoryManager:
                fromSupplierToMemoryManager(event.priority, event.requestId);
                break;
            case fromMemoryManagerToTrashBin:
                fromMemoryManagerToTrashBin(event.priority, event.requestId);
                break;
            case fromBufferToConsumerManager:
                fromBufferToConsumerManager(event.requestId);
                break;
            case fromBufferToMemoryManager:
                fromBufferToMemoryManager(event.index);
                break;
            case fromConsumerManagerToConsumer:
                fromConsumerManagerToConsumer(event.priority, event.requestId, event.index);
                break;
            case fromMemoryManagerToBuffer:
                fromMemoryManagerToBuffer(event.priority, event.requestId, event.isPackage, event.index);
                break;
            case consumerFree:
                consumerFree(event.priority, event.requestId, event.index);
                break;
        }
        progressLabel.setText(simulator.getCounter() + " / " + simulator.getN());
    }

    private GraphicEvent getNextEvent() {
        if (skipEnabled.get()) {
            return null;
        }
        GraphicEvent event = GraphicAdapter.INSTANCE.getLastGraphicEvent();
        if (event == null) {
            boolean isOver = !simulator.simulationStep();
            if (isOver) {
                simulationIsOver();
                return null;
            }
            event = GraphicAdapter.INSTANCE.getLastGraphicEvent();
        }
        return event;
    }

    public void fromSupplierToMemoryManager(final int priority, final int requestId) {
        suppliersContainer.scrollTo(priority);
        final Request request = new Request(requestId, priority, () -> showRequestInfo(requestId), cellSize);
        moveRequest(request, suppliers[priority], memoryManager, 1f);
    }

    private void showRequestInfo(int requestId) {
        infoWindow.setTitle("Запрос " + requestId, "Запрос " + requestId);
        tsp.setText(simulator.requestInfo(requestId), simulator.requestInfo(requestId));
        infoWindow.show();
    }

    public void fromMemoryManagerToTrashBin(final int priority, final int requestId) {
        final Request request = new Request(requestId, priority, () -> showRequestInfo(requestId), cellSize);
        moveRequest(request, memoryManager, trashBin, 1f);
    }

    public void fromBufferToConsumerManager(final int requestId) {
        int ind = 0;
        for (int i = 0; i < bufferCells.length; ++i) {
            if (bufferCells[i].getRequest() != null && bufferCells[i].getRequest().getId() == requestId) {
                ind = i;
                break;
            }
        }
        final int index = ind;
        bufferContainer.scrollTo(ind);
        addAction(Actions.sequence(Actions.delay(speed + 1f), Actions.run(() -> {
            final Request request = bufferCells[index].getRequest();
            bufferCells[index].setRequest(null, false);
            moveRequest(request, bufferCells[index], consumerManager, 0f);
        })));
    }

    public void fromBufferToMemoryManager(final int index) {
        bufferContainer.scrollTo(index);
        addAction(Actions.sequence(Actions.delay(speed + 1f), Actions.run(() -> {
            final Request request = bufferCells[index].getRequest();
            bufferCells[index].setRequest(null, false);
            moveRequest(request, bufferCells[index], memoryManager, 0f);
        })));
    }

    public void fromConsumerManagerToConsumer(final int priority, final int requestId, final int index) {
        consumersContainer.scrollTo(index);
        addAction(Actions.sequence(Actions.delay(speed + 1f), Actions.run(() -> {
            final Request request = new Request(requestId, priority, () -> showRequestInfo(requestId), cellSize);
            moveRequest(request, consumerManager, consumers[index], 0f);
        })));
    }

    public void fromMemoryManagerToBuffer(final int priority, final int requestId, boolean isPackage, final int index) {
        for (BufferCell bufferCell : bufferCells) {
            if (bufferCell.getRequest() != null)
                if (bufferCell.getRequest().getPriority() == priority) {
                    bufferCell.setPackage(isPackage);
                }
        }
        bufferContainer.scrollTo(index);
        final Request request = new Request(requestId, priority, () -> showRequestInfo(requestId), cellSize);
        addAction(Actions.sequence(moveRequestActions(request, memoryManager, bufferCells[index], 1f + speed), Actions.delay(3f * speed), Actions.run(() -> {
            final Request r = new Request(requestId, priority, () -> showRequestInfo(requestId), cellSize);
            bufferCells[index].setRequest(r, isPackage);
        })));
    }

    public void consumerFree(final int priority, final int requestId, final int index) {
        consumersContainer.scrollTo(index);
        final Request request = new Request(requestId, priority, () -> showRequestInfo(requestId), cellSize);
        addAction(Actions.sequence(Actions.delay(speed + 1f),
                Actions.parallel(
                        moveRequestActions(request, consumers[index], tmpRequest, 0f)
                )
                ));
    }

    public Action moveRequestActions(final Request request1, final Actor from, final Actor to, final float delay) {
        if (request1 == null) {
            return Actions.sequence();
        }
        final Request request = new Request(request1.getId(), request1.getPriority(), request1.getAction(), cellSize);
        return Actions.sequence(
                Actions.delay(delay),
                Actions.run(() -> {
                    final float fromX = from.getParent().getParent().getParent().getX() + from.getParent().getParent().getX() + from.getParent().getX()  + from.getX() + from.getWidth() / 2f - cellSize / 2f;
                    final float fromY = from.getParent().getParent().getParent().getY() + from.getParent().getParent().getY() + from.getParent().getY() + from.getY() + from.getHeight() / 2f - cellSize / 2f;
                    final float toX = to.getParent().getParent().getParent().getX() + to.getParent().getParent().getX() + to.getParent().getX()  + to.getX() + to.getWidth() / 2f - cellSize / 2f;
                    final float toY = to.getParent().getParent().getParent().getY() + to.getParent().getParent().getY() + to.getParent().getY() + to.getY() + to.getHeight() / 2f - cellSize / 2f;

                    request.setColor(new Color(1f, 1f, 1f, 0f));
                    request.setBounds(fromX, fromY, cellSize, cellSize);

                    request.addAction(Actions.sequence(
                            Actions.alpha(1f, speed * 2f),
                            Actions.moveTo(toX, toY, speed),
                            Actions.alpha(0f, speed),
                            Actions.removeActor()
                    ));
                    getStage().addActor(request);
                })
        );
    }

    public void moveRequest(final Request request, final Actor from, final Actor to, final float delay) {
        addAction(moveRequestActions(request, from, to, delay));
    }

}
