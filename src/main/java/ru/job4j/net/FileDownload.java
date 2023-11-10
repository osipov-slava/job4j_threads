package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class FileDownload {
    public static void main(String[] args) throws Exception {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                System.out.println("Read 512 bytes : " + (System.nanoTime() - downloadAt) + " nano.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Files.size(file.toPath()) + " bytes");
    }
}
