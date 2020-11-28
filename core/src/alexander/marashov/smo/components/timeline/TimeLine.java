package alexander.marashov.smo.components.timeline;

import org.jetbrains.annotations.NotNull;

import java.util.PriorityQueue;
import java.util.Queue;

public final class TimeLine {

    @NotNull
    private final Queue<Event> eventsQueue;

    private double time;

    public TimeLine() {
        this.eventsQueue = new PriorityQueue<>();
        this.time = 0.;
    }

    public final void addEvent(@NotNull final Event event) {
        this.eventsQueue.add(event);
    }

    public final Event getNextEvent() {
        if (eventsQueue.isEmpty()) {
            return null;
        }

        final Event event = this.eventsQueue.poll();
        time = event.timestamp;
        return event;
    }

    public double getTime() {
        return time;
    }

    public void clear() {
        eventsQueue.clear();
        time = 0.;
    }
}
