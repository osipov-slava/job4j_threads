package ru.job4j.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedUsage {

    public static void main(String[] args) throws IOException {

        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream();

        Thread firstThread = new Thread(() -> {
            try {
                out.write("Job4j".getBytes());
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread secondThread = new Thread(() -> {
            try {
                int ch;
                while ((ch = in.read()) != -1) {
                    System.out.print((char) ch);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        in.connect(out);
        firstThread.start();
        secondThread.start();
    }
}
