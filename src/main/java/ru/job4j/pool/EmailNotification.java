package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public EmailNotification() {

    }

    public void emailTo(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Execute " + Thread.currentThread().getName());
                String subject = String.format("Notification %s to email %s", user.username(), user.email());
                String body = String.format("Add a new event to %s", user.username());
                send(subject, body, user.email());
            }
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {
        System.out.println(subject + " " + body + " " + email);
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        for (int i = 0; i < 20; i++) {
            emailNotification.emailTo(new User("User" + i, "email" + i));
        }
        emailNotification.close();
    }
}
