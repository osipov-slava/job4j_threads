package ru.job4j.blocking.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int volume;

    public SimpleBlockingQueue(int volume) {
        this.volume = volume;
    }

    public boolean isEmpty() {
        synchronized (this) {
            return queue.isEmpty();
        }
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == volume) {
                this.wait();
            }
            queue.offer(value);
            notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
            }
            T result = queue.poll();
            notifyAll();
            return result;
        }
    }
}
