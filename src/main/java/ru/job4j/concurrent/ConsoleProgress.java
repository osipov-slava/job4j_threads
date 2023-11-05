package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        var i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\rLoading ... " + process[i++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (i == process.length) {
                i = 0;
            }
        }
    }
}
