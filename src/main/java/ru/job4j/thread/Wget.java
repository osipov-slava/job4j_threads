package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String fileName;
    private final int speed;

    public Wget(String url, String fileName, int speed) {
        this.url = url;
        this.fileName = fileName;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(fileName);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            int pack = Math.min(speed, 512);
            var dataBuffer = new byte[pack];
            int bytesRead;
            int bytesWritten = 0;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long downloadAt;
                out.write(dataBuffer, 0, bytesRead);
                bytesWritten += bytesRead;
                System.out.println("written " + bytesWritten + " bytes");
                if (bytesWritten >= speed) {
                    downloadAt = System.currentTimeMillis();
                    long spentTime = downloadAt - startAt;
                    if (spentTime < 1000) {
                        System.out.println("sleep at " + (1000 - spentTime) + " ms");
                        Thread.sleep(1000 - spentTime);
                    }
                    bytesWritten = 0;
                    startAt = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void urlValidation(String url) throws InvalidInputException {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new InvalidInputException("URL is invalid");
        }
    }

    public static int speedParsing(String str) throws InvalidInputException {
        int speed;
        try {
            speed = Integer.parseInt(str);
            if (speed < 0) {
                throw new InvalidInputException("Speed is invalid");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Speed is invalid");
        }
        return speed;
    }

    public static void main(String[] args) throws InterruptedException, NumberFormatException, InvalidInputException {
        String url = args[0];
        int speed = speedParsing(args[2]);
        urlValidation(url);
        Thread wget = new Thread(new Wget(url, args[1], speed));
        wget.start();
        wget.join();
    }
}
