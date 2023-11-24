package ru.job4j;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }

    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int countGoal = 3;
        CountBarrier countBarrier = new CountBarrier(countGoal);
        Runnable runnable = (
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " finished");
                });
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        for (int i = 0; i < countGoal; i++) {
            Thread.sleep(500);
            countBarrier.count();
            System.out.println("count is " + (i + 1));
        }
    }
}
