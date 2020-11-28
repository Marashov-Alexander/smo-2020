package alexander.marashov.smo.components;

import alexander.marashov.smo.components.generators.Request;
import alexander.marashov.smo.components.generators.RequestConsumer;
import alexander.marashov.smo.components.generators.RequestSupplier;
import alexander.marashov.smo.components.managers.MemoryManager;
import alexander.marashov.smo.components.managers.SelectionManager;
import alexander.marashov.smo.components.timeline.Event;
import alexander.marashov.smo.components.timeline.TimeLine;
import alexander.marashov.smo.elements.GraphicAdapter;
import alexander.marashov.smo.elements.GraphicEvent;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Simulator {

//    private static final Logger log = LoggerFactory.getLogger(Simulator.class);
    private static final double delta = 0.1;
    private static final double ta = 1.643;

    private final MemoryManager memoryManager;
    private final SelectionManager selectionManager;
    private final TimeLine timeLine;

    private final RequestSupplier[] suppliers;
    private final RequestConsumer[] consumers;

    private int n;
    private int counter;
    private boolean stopFlag;

    private double prevRejectProbability = Double.NEGATIVE_INFINITY;

    private final GraphicAdapter graphicAdapter;

    public Simulator(final int bufferSize, final double[] consumerIntensities, final double[] supplierIntensities) {

        graphicAdapter = GraphicAdapter.INSTANCE;

        this.timeLine = new TimeLine();

        this.suppliers = new RequestSupplier[supplierIntensities.length];
        for (int i = 0; i < supplierIntensities.length; ++i) {
            this.suppliers[i] = new RequestSupplier(i, supplierIntensities[i], i);
        }

        this.consumers = new RequestConsumer[consumerIntensities.length];
        for (int i = 0; i < consumerIntensities.length; ++i) {
            this.consumers[i] = new RequestConsumer(i, consumerIntensities[i]);
        }

        this.memoryManager = new MemoryManager(bufferSize);
        this.selectionManager = new SelectionManager(this.memoryManager::pop, consumers);

        clear();
    }

    public void clear() {
        this.timeLine.clear();
        for (RequestSupplier supplier : suppliers) {
            supplier.clear();
        }

        for (RequestConsumer consumer : consumers) {
            consumer.clear();
        }

        memoryManager.clear();
        selectionManager.clear();

        graphicAdapter.clear();
    }

    public void simulate() {
        while (simulationStep()) {
//            log.debug("State: counter = {}, n = {}", counter, n);
        }
    }

    public boolean hasEnoughPrecision() {
        simulate();

        final double rejectProbability = rejectProbability();
        final double rejectProbabilityDelta = Math.abs(rejectProbability - prevRejectProbability);
        boolean result = rejectProbabilityDelta <= delta;
//        System.out.println("Reject probability = " + rejectProbability);
//        System.out.println("Reject probability old = " + prevRejectProbability);
        prevRejectProbability = rejectProbability;
        final int iterations = Math.min(5000, (int) Math.ceil(
                ta * ta * (1 - rejectProbability)
                        / (rejectProbability * delta * delta)));

//        System.out.println("Reject probability delta = " + rejectProbabilityDelta);
        if (!result)
            simulateDebug(iterations);

        return result;
    }

    public void simulateDebug(final int n) {
        this.n = n;
        this.counter = 0;
        this.stopFlag = false;
        clear();

        for (final RequestSupplier supplier : this.suppliers) {
            timeLine.addEvent(supplier.generateEvent(timeLine.getTime()));
        }
    }

    public double rejectProbability() {
        final double rejectedCount = Arrays.stream(suppliers)
                .mapToInt(RequestSupplier::getRejectedCount)
                .sum();
        final double totalGeneratedCount = Arrays.stream(suppliers)
                .mapToInt(RequestSupplier::getGeneratedCount)
                .sum();
        return rejectedCount / totalGeneratedCount;
    }

    public SimulationResult systemState() {
        final double rejectProbability = rejectProbability();

        final int[] generatedCount = Arrays.stream(suppliers)
                .mapToInt(RequestSupplier::getGeneratedCount)
                .toArray();

        final double[] averageInSystemTime = Arrays.stream(suppliers)
                .mapToDouble(RequestSupplier::averageInSystemTime)
                .toArray();

        final double[] averageInBufferTime = Arrays.stream(suppliers)
                .mapToDouble(RequestSupplier::averageInBufferTime)
                .toArray();

        final double[] averageInConsumerTime = Arrays.stream(suppliers)
                .mapToDouble(RequestSupplier::averageInConsumerTime)
                .toArray();

        final double[] varianceBufferTime = Arrays.stream(suppliers)
                .mapToDouble(RequestSupplier::varianceBufferTime)
                .toArray();

        final double[] varianceConsumerTime = Arrays.stream(suppliers)
                .mapToDouble(RequestSupplier::varianceConsumerTime)
                .toArray();

        final double[] supplierRejectProbability = Arrays.stream(suppliers)
                .mapToDouble(RequestSupplier::rejectProbability)
                .toArray();

        final double[] consumersBusyCoefficients = Arrays.stream(consumers)
                .mapToDouble(consumer -> consumer.getBusyTime() / timeLine.getTime())
                .toArray();

        return new SimulationResult(
                getN(),
                rejectProbability,
                generatedCount,
                supplierRejectProbability,
                averageInSystemTime,
                averageInBufferTime,
                averageInConsumerTime,
                varianceConsumerTime,
                varianceBufferTime,
                consumersBusyCoefficients
        );
    }

    public String getSimulationResults() {
        SimulationResult results = systemState();
        final StringBuilder builder = new StringBuilder();
        builder.append("==================\n");
        builder.append("  Iterations count: ").append(n).append('\n');
        builder.append("Reject probability: ").append(results.rejectProbability).append('\n');
        builder.append("==================\n");

        builder.append("Характеристики источников ВС").append('\n');
        final int cellLength = 17;
        final String[] strings = "Ном источника,количество заявок,P отказа,T пребывания,T в буфере,T в приборе,Д в буфере,Д в приборе".split(",");
        for (final String string : strings) {
            builder.append(printCell(string, cellLength));
        }
        builder.append('\n');
        for (int i = 0; i < suppliers.length; ++i) {
            builder.append(printCell(Integer.toString(i+1), cellLength));
            builder.append(printCell(Integer.toString(results.generatedCount[i]), cellLength));
            builder.append(printCell(Double.toString(results.supplierRejectProbability[i]), cellLength));
            builder.append(printCell(Double.toString(results.averageInSystemTime[i]), cellLength));
            builder.append(printCell(Double.toString(results.averageInBufferTime[i]), cellLength));
            builder.append(printCell(Double.toString(results.averageInConsumerTime[i]), cellLength));
            builder.append(printCell(Double.toString(results.varianceBufferTime[i]), cellLength));
            builder.append(printCell(Double.toString(results.varianceConsumerTime[i]), cellLength));
            builder.append('\n');
        }

        builder.append('\n');
        builder.append("Характеристики приборов ВС").append('\n');
        builder.append(printCell("Ном прибора", 10));
        builder.append(printCell("Коэффициент использования", 25));
        builder.append('\n');
        for (int i = 0; i < consumers.length; ++i) {
            builder.append(printCell(Integer.toString(i+1), 10));
            builder.append(printCell(Double.toString(results.consumersBusyCoefficients[i]), 25));
            builder.append('\n');
        }

        return builder.toString();
    }

    private static String printCell(final String string, final int length) {
        final StringBuilder builder = new StringBuilder(string);
        while (builder.length() < length) {
            builder.append(" ");
        }
        while (builder.length() > length) {
            builder.deleteCharAt(length);
        }
        builder.append(" | ");
        return builder.toString();
    }

    public boolean simulationStep() {

        final Event event = timeLine.getNextEvent();

        if (event == null) {
//            log.debug("No more events. Simulation is over.");
            return false;
        }
        if (stopFlag && event.type == Event.EventType.SUPPLIER_EVENT) {
//            log.debug("Simulation is stopped. Skipping SUPPLIER_EVENT.");
            return true;
        }

        final Request request = event.actions.get();
//        log.debug("Next event is {}, request = {}", event, request);

        switch (event.type) {
            case SUPPLIER_EVENT: {
//                log.debug("SUPPLIER_EVENT");

                graphicAdapter.addGraphicEvent(new GraphicEvent(
                        GraphicEvent.GraphicEventType.fromSupplierToMemoryManager,
                        request.priority,
                        request.id,
                        null,
                        request.priority
                ));

                final int oldest = memoryManager.oldestIndex();
                final Request rejected = memoryManager.push(request);
                if (rejected != null) {
//                    log.debug("{} rejected", rejected);

                    graphicAdapter.addGraphicEvent(new GraphicEvent(
                            GraphicEvent.GraphicEventType.fromBufferToMemoryManager,
                            null,
                            null,
                            null,
                            oldest
                    ));
                    graphicAdapter.addGraphicEvent(new GraphicEvent(
                            GraphicEvent.GraphicEventType.fromMemoryManagerToTrashBin,
                            rejected.priority,
                            rejected.id,
                            null,
                            null
                    ));

                    suppliers[rejected.priority].reject(rejected, event.timestamp);
                }

                graphicAdapter.addGraphicEvent(new GraphicEvent(
                        GraphicEvent.GraphicEventType.fromMemoryManagerToBuffer,
                        request.priority,
                        request.id,
                        request.priority == selectionManager.getPackageIndex(),
                        memoryManager.indexOf(request)
                ));

                timeLine.addEvent(event.initiator.generateEvent(timeLine.getTime()));
                break;
            }
            case CONSUMER_FREE_EVENT: {
//                log.debug("CONSUMER_FREE_EVENT");

                graphicAdapter.addGraphicEvent(new GraphicEvent(
                        GraphicEvent.GraphicEventType.consumerFree,
                        request.priority,
                        request.id,
                        null,
                        event.initiator.id
                ));

                counter++;
//                System.out.println("DELTA" + event.timestamp + " - " + request.getPutOnServiceTimestamp());
                ((RequestConsumer) event.initiator).freed(event.timestamp - request.getPutOnServiceTimestamp());
                suppliers[request.priority].done(request, event.timestamp);
                break;
            }
        }

        final RequestConsumer freeConsumer = selectionManager.freeConsumer();
//        log.debug("Free consumer is {}", freeConsumer);
        if (freeConsumer != null) {
            final Request next = selectionManager.selectNext();
//            log.debug("Next request is {}", next);
            if (next != null) {

                graphicAdapter.addGraphicEvent(new GraphicEvent(
                        GraphicEvent.GraphicEventType.fromBufferToConsumerManager,
                        null,
                        next.id,
                        null,
                        null
                ));

                graphicAdapter.addGraphicEvent(new GraphicEvent(
                        GraphicEvent.GraphicEventType.fromConsumerManagerToConsumer,
                        next.priority,
                        0,
                        null,
                        freeConsumer.id
                ));

                suppliers[next.priority].took(next, event.timestamp);
                next.setPutOnServiceTimestamp(event.timestamp);
                freeConsumer.consume(next);
                timeLine.addEvent(freeConsumer.generateEvent(timeLine.getTime()));
            }
        }

        if (counter >= n) {
            stopFlag = true;
        }
        return true;
    }

    public String supplierInfo(int index) {
        return suppliers[index].toString();
    }

    public String memManagerInfo() {
        return memoryManager.toString();
    }

    public String chooseManagerInfo() {
        return selectionManager.toString();
    }

    public String consumerInfo(int index) {
        return consumers[index].toString();
    }

    public String rejectInfo() {
        return "Отказов = " + Arrays.stream(suppliers)
                .mapToInt(RequestSupplier::getRejectedCount)
                .sum();
    }

    public String requestInfo(int requestId) {
        for (int i = 0; i < Request.requests.length; ++i) {
            if (Request.requests[i] != null && Request.requests[i].id == requestId) {
                return Request.requests[i].toString();
            }
        }
        return "Not found :(";
    }

    public int getN() {
        return n;
    }

    public int getCounter() {
//        System.out.println("COUNTER BL" + counter);
        return counter;
    }

    public int getSuppliersCount() {
        return suppliers.length;
    }

    public int getBufferSize() {
        return memoryManager.getBufferSize();
    }

    public int getConsumersCount() {
        return consumers.length;
    }

    public static class SimulationResult {
        public int iterationsCount;
        public double rejectProbability;
        public int[] generatedCount;
        public double[] supplierRejectProbability;
        public double[] averageInSystemTime;
        public double[] averageInBufferTime;
        public double[] averageInConsumerTime;
        public double[] varianceConsumerTime;
        public double[] varianceBufferTime;
        public double[] consumersBusyCoefficients;

        public SimulationResult() {

        }

        public SimulationResult(
                final int iterationsCount,
                final double rejectProbability,
                final int[] generatedCount,
                final double[] supplierRejectProbability,
                final double[] averageInSystemTime,
                final double[] averageInBufferTime,
                final double[] averageInConsumerTime,
                final double[] varianceConsumerTime,
                final double[] varianceBufferTime,
                final double[] consumersBusyCoefficients
        ) {
            this.iterationsCount = iterationsCount;
            this.rejectProbability = rejectProbability;
            this.generatedCount = generatedCount;
            this.supplierRejectProbability = supplierRejectProbability;
            this.averageInSystemTime = averageInSystemTime;
            this.averageInBufferTime = averageInBufferTime;
            this.averageInConsumerTime = averageInConsumerTime;
            this.varianceConsumerTime = varianceConsumerTime;
            this.varianceBufferTime = varianceBufferTime;
            this.consumersBusyCoefficients = consumersBusyCoefficients;
        }
    }

}
