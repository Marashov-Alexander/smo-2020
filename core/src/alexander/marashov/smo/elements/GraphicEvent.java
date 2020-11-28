package alexander.marashov.smo.elements;

public class GraphicEvent {
    public final Integer priority;
    public final Integer requestId;
    public final Boolean isPackage;
    public final Integer index;
    public final GraphicEventType eventType;

    public GraphicEvent(GraphicEventType eventType, Integer priority, Integer requestId, Boolean isPackage, Integer index) {
        this.priority = priority;
        this.requestId = requestId;
        this.isPackage = isPackage;
        this.index = index;
        this.eventType = eventType;
    }

    public enum GraphicEventType {
        fromSupplierToMemoryManager,
        fromMemoryManagerToTrashBin,
        fromBufferToConsumerManager,
        fromBufferToMemoryManager,
        fromConsumerManagerToConsumer,
        fromMemoryManagerToBuffer,
        consumerFree,
    }
}
