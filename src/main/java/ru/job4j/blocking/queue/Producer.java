package ru.job4j.blocking.queue;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Producer<T> implements Runnable {
    private final SimpleBlockingQueue<T> queue;
    private final T object;

    public Producer(SimpleBlockingQueue<T> queue, T object) {
        this.queue = queue;
        this.object = object;
    }

    @Override
    public void run() {
        try {
            queue.offer(object);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
