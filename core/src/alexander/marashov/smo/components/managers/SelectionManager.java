package alexander.marashov.smo.components.managers;

import alexander.marashov.smo.components.generators.Request;
import alexander.marashov.smo.components.generators.RequestConsumer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static alexander.marashov.smo.components.generators.Request.ANY_PRIORITY;

public class SelectionManager {

//    private static final Logger log = LoggerFactory.getLogger(SelectionManager.class);

    private final Function<Integer, Request> chooseRequestByPriority;

    private final RequestConsumer[] consumers;
    private int pointer;

    private int currentPackage;


    public SelectionManager(
            final Function<Integer, Request> chooseRequestByPriority,
            final RequestConsumer[] consumers
    ) {
        this.chooseRequestByPriority = chooseRequestByPriority;
        this.currentPackage = ANY_PRIORITY;
        this.consumers = consumers;
    }

    public RequestConsumer freeConsumer() {
        final int oldPointer = pointer;
        while (consumers[pointer].isBusy()) {
            pointer = (pointer + 1) % consumers.length;
            if (pointer == oldPointer) {
                return null;
            }
        }
        return consumers[pointer];
    }

    public Request selectNext() {
        final Request request = this.chooseRequestByPriority.apply(this.currentPackage);
        if (request == null && this.currentPackage != ANY_PRIORITY) {
//            log.debug("Selected request is null. Package {} is empty.", this.currentPackage);
            this.currentPackage = ANY_PRIORITY;
            final Request selected = this.chooseRequestByPriority.apply(this.currentPackage);
//            log.debug("Selected request is {}", selected);
            return selected;
        }
        this.currentPackage = request == null ? ANY_PRIORITY : request.priority;
//        log.debug("Selected request is {}, current package = {}", request, currentPackage);
        return request;
    }


    public int getPackageIndex() {
        return currentPackage;
    }

    @Override
    public String toString() {
        return "SelectionManager(pointer = " + pointer + ", currentPackage = " + currentPackage + ")";
    }

    public void clear() {
        pointer = 0;
        currentPackage = -1;
    }
}
