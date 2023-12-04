package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CASCountTest {
    @Test
    public void when2threads1000inc() {
        CASCount casCount = new CASCount();
        Runnable runnable = (
                () -> {
                    for (int i = 0; i < 500; i++) {
                        casCount.increment();
                    }
                });
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertEquals(1000, casCount.get());
    }
}