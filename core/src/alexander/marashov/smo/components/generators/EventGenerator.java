package alexander.marashov.smo.components.generators;

import alexander.marashov.smo.components.timeline.Event;

public abstract class EventGenerator {

    public final int id;
    public final double intensity;

    public EventGenerator(final int id, final double intensity) {
        this.id = id;
        this.intensity = intensity;
    }

    abstract double generateDelay();

    public abstract Event generateEvent(final double currentTime);

    public abstract void clear();
}
