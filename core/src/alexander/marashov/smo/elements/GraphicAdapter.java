package alexander.marashov.smo.elements;

import com.badlogic.gdx.utils.Queue;

public class GraphicAdapter {

    private final Queue<GraphicEvent> eventsQueue;

    public final static GraphicAdapter INSTANCE = new GraphicAdapter();

    private GraphicAdapter() {
        eventsQueue = new Queue<>(20);
    }

    public void addGraphicEvent(final GraphicEvent event) {
//        System.out.println("AAA " + event.eventType);
        eventsQueue.addLast(event);
    }

    public GraphicEvent getLastGraphicEvent() {
        if (eventsQueue.isEmpty()) {
            return null;
        } else {
            return eventsQueue.removeFirst();
        }
    }

    public void clear() {
        eventsQueue.clear();
    }
}
