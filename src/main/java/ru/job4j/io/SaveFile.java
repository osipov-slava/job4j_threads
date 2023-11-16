package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (var in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
        }
    }
}

