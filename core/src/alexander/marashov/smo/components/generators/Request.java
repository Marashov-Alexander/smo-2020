package alexander.marashov.smo.components.generators;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public final class Request implements Comparable<Request> {

    public static final int ANY_PRIORITY = -1;
    public static final AtomicInteger globalId = new AtomicInteger(0);

    public static final Request[] requests = new Request[100];
    public static int pointer = 0;

    public final int id;
    public final int priority;
    public final double timestamp;
    private double putOnServiceTimestamp;

    public Request(final int priority, final double timestamp) {
        this.id = globalId.getAndIncrement();
        this.priority = priority;
        this.timestamp = timestamp;

        requests[pointer] = this;
        pointer = (pointer + 1) % requests.length;
    }

    public void setPutOnServiceTimestamp(final double putOnServiceTimestamp) {
        this.putOnServiceTimestamp = putOnServiceTimestamp;
    }

    public double getPutOnServiceTimestamp() {
        return putOnServiceTimestamp;
    }

    @Override
    public int compareTo(@NotNull final Request request) {
        return Double.compare(this.timestamp, request.timestamp);
    }

    @Override
    public String toString() {
        return "\nRequest(id = " + id + ", timestamp = " + timestamp + ", priority = " + priority + ", putOnServiceTimestamp = " + putOnServiceTimestamp + ")";
    }
}
