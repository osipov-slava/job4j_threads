package ru.job4j.blocking.queue;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Consumer<T> implements Runnable {
    private final SimpleBlockingQueue<T> queue;

    public Consumer(SimpleBlockingQueue<T> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.poll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
