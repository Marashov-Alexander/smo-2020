package alexander.marashov.smo.components.generators;

import alexander.marashov.smo.components.timeline.Event;

public class RequestSupplier extends EventGenerator {

    private final int priority;
    private int generatedCount;
    private int rejectedCount;
    private int doneCount;

    private double inBufferTime;
    private double inBufferTimeSquare;

    private double inConsumerTime;
    private double inConsumerTimeSquare;

    public RequestSupplier(final int id, final double intensity, final int priority) {
        super(id, intensity);
        this.priority = priority;

        this.generatedCount = 0;
        this.doneCount = 0;
        this.rejectedCount = 0;

        this.inBufferTime = 0.;
        this.inBufferTimeSquare = 0.;

        this.inConsumerTime = 0.;
        this.inConsumerTimeSquare = 0.;
    }

    @Override
    public double generateDelay() {
        return 5 - Math.log(Math.random()) * 20 / intensity;
    }

    @Override
    public Event generateEvent(final double currentTime) {
        generatedCount++;
        final double timestamp = currentTime + generateDelay();
        return new Event(
                Event.EventType.SUPPLIER_EVENT,
                this,
                timestamp,
                () -> new Request(priority, timestamp)
        );
    }

    @Override
    public void clear() {
        this.generatedCount = 0;
        this.doneCount = 0;
        this.rejectedCount = 0;

        this.inBufferTime = 0.;
        this.inBufferTimeSquare = 0.;

        this.inConsumerTime = 0.;
        this.inConsumerTimeSquare = 0.;
    }

    public int getGeneratedCount() {
        return this.generatedCount;
    }

    public int getRejectedCount() {
        return this.rejectedCount;
    }

    public double rejectProbability() {
        return (double) rejectedCount / generatedCount;
    }

    public double averageInSystemTime() {
        return averageInBufferTime() + averageInConsumerTime();
    }

    // D(x) = M(x^2) - (M(x))^2
    public double varianceBufferTime() {
        return inBufferTimeSquare / generatedCount
                - (inBufferTime / generatedCount) * (inBufferTime / generatedCount);
    }

    public double varianceConsumerTime() {
        return inConsumerTimeSquare / doneCount
                - (inConsumerTime / doneCount) * (inConsumerTime / doneCount);
    }


    public double averageInBufferTime() {
        return inBufferTime / generatedCount;
    }

    public double averageInConsumerTime() {
        return inConsumerTime / generatedCount;
    }

    public double getInConsumerTime() {
        return inConsumerTime;
    }

    public void reject(final Request request, final double time) {
        assert request.priority == this.priority;
        rejectedCount++;
        inBufferTime += (time - request.timestamp);
        inBufferTimeSquare += (time - request.timestamp) * (time - request.timestamp);
    }

    public void done(Request request, double time) {
        assert request.priority == this.priority;
        doneCount++;
        inConsumerTime += (time - request.timestamp);
        inConsumerTimeSquare += (time - request.timestamp) * (time - request.timestamp);
    }

    public void took(final Request request, final double time) {
        assert request.priority == this.priority;
        inBufferTime += (time - request.timestamp);
        inBufferTimeSquare += (time - request.timestamp) * (time - request.timestamp);
    }

    @Override
    public String toString() {
        return "RequestSupplier(priority = " + priority + ", generatedCount = " + generatedCount + ", rejectedCount = " + rejectedCount + ")";
    }
}
