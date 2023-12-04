package ru.job4j.pool;

import ru.job4j.blocking.queue.SimpleBlockingQueue;

public class ConsumerJob implements Runnable {
    private final SimpleBlockingQueue<Runnable> queue;

    public ConsumerJob(SimpleBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                queue.poll().run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
