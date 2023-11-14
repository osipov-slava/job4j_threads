package ru.job4j.thread.threadlocal;

public class SecondThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Это поток 2.");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
