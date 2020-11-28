package alexander.marashov.smo.components.managers;

import alexander.marashov.smo.components.generators.Request;
import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MemoryManager {

//    private static final Logger log = LoggerFactory.getLogger(MemoryManager.class);

    private final Request[] requestBuffer;

    public MemoryManager(final int bufferSize) {
        this.requestBuffer = new Request[bufferSize];
    }

    public final int oldestIndex() {
        Request oldest = null;
        int oldestIndex = -1;

        for (int i = 0; i < requestBuffer.length; ++i) {
            if (requestBuffer[i] != null && (oldest == null || requestBuffer[i].compareTo(oldest) < 0)) {
                oldest = requestBuffer[i];
                oldestIndex = i;
            }
        }
        return oldestIndex;
    }

    public final Request push(@NotNull final Request request) {
//        log.debug("Push method: buffer state is {}", Arrays.toString(requestBuffer));

        Request oldest = null;
        int oldestIndex = -1;

        for (int i = 0; i < requestBuffer.length; ++i) {
            if (requestBuffer[i] == null) {
//                log.debug("Buffer has empty place at {} position for {}", i, request);
                requestBuffer[i] = request;
                return null;
            } else if (oldest == null || requestBuffer[i].compareTo(oldest) < 0) {
                oldest = requestBuffer[i];
                oldestIndex = i;
            }

        }
//        log.debug("Buffer is full. The oldest {} is dropped", oldest);
        requestBuffer[oldestIndex] = request;
        return oldest;
    }

    public final int indexOf(@NotNull final Request request) {
        for (int i = 0; i < requestBuffer.length; ++i) {
            if (requestBuffer[i] == request) {
                return i;
            }
        }
        return -1;
    }

    public final Request pop(final int priority) {
//        log.debug("Pop method: buffer state is {}", Arrays.toString(requestBuffer));

        Request maxPriorityRequest = null;
        int maxPriorityRequestIndex = -1;
        for (int i = 0; i < requestBuffer.length; ++i) {
            final Request current = requestBuffer[i];
            if (current != null) {
                if (current.priority == priority) {
                    maxPriorityRequest = current;
                    maxPriorityRequestIndex = i;
                    break;
                } else if (maxPriorityRequest == null || maxPriorityRequest.priority < current.priority) {
                    maxPriorityRequest = current;
                    maxPriorityRequestIndex = i;
                }
            }
        }

        if (maxPriorityRequest == null) {
//            log.debug("Buffer is empty");
            return null;
        } else {
            requestBuffer[maxPriorityRequestIndex] = null;
//            log.debug("Pop method returned {}", maxPriorityRequest);
            return maxPriorityRequest;
        }
    }

    @Override
    public String toString() {
        return "MemoryManager( buffer = " + Arrays.toString(requestBuffer) + ")";
    }

    public int getBufferSize() {
        return requestBuffer.length;
    }

    public void clear() {
        Arrays.fill(requestBuffer, null);
    }
}
