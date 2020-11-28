package alexander.marashov.smo.components.generators;

import alexander.marashov.smo.components.timeline.Event;

public class RequestConsumer extends EventGenerator {
    
    private Request currentRequest;
    private int servedCount;

    private double busyTime;

    public RequestConsumer(final int id, final double intensity) {
        super(id, intensity);
        currentRequest = null;
        servedCount = 0;
        busyTime = 0;
    }

    @Override
    public double generateDelay() {
        return (Math.max(Math.random(), 0.5) * 30 / intensity + 5);
    }

    @Override
    public Event generateEvent(final double currentTime) {
        final double eventTime = currentTime + generateDelay();
        return new Event(
                Event.EventType.CONSUMER_FREE_EVENT,
                this,
                eventTime,
                () -> {
                    final Request request = this.currentRequest;
                    this.currentRequest = null;
                    servedCount++;
                    return request;
                }
        );
    }

    @Override
    public void clear() {
        currentRequest = null;
        servedCount = 0;
        busyTime = 0;
    }

    public boolean isBusy() {
        return currentRequest != null;
    }

    public void consume(final Request request) {
        assert !this.isBusy();
        this.currentRequest = request;
    }

    public void freed(final double deltaTime) {
        this.busyTime += deltaTime;
    }

    public int getServedCount() {
        return servedCount;
    }

    public double getBusyTime() {
        return busyTime;
    }

    @Override
    public String toString() {
        return "RequestConsumer(servedCount = " + servedCount + ")";
    }

}
