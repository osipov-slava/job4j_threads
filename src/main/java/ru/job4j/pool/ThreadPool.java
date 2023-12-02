package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.blocking.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int maxNoOfTasks) {
        tasks = new SimpleBlockingQueue<>(maxNoOfTasks);
        int sizeThreadPool = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < sizeThreadPool; i++) {
            Thread thread = new Thread(new ConsumerJob(tasks));
            thread.start();
            threads.add(thread);
        }
    }

    public synchronized void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
