package ru.job4j.pool;

public class ThreadPoolUse {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(5);
        for (int i = 0; i < 50; i++) {
            threadPool.work(() ->
                    System.out.println(Thread.currentThread().getName() + " is working"));
        }
        threadPool.shutdown();
    }
}
