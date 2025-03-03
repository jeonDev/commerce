package com.commerce.core.event.type.local;

import com.commerce.core.event.consumer.local.LocalEventListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
@Profile("test")
public class LocalEventQueue implements EventQueue {

    private final BlockingQueue<Object> queue;
    private final LocalEventListener localEventListener;

    public LocalEventQueue(LocalEventListener localEventListener) {
        this.queue = new ArrayBlockingQueue<>(50);
        this.localEventListener = localEventListener;
    }

    @Override
    public void offer(Object o) {
        queue.offer(o);
    }

    @Override
    public Object poll() {
        QueueData result = (QueueData) queue.poll();

        localEventListener.consume(result);
        return result;
    }
}
