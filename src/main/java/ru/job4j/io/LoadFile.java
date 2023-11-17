package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class LoadFile {
    private final File file;

    public LoadFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> predicate) throws IOException {
        var output = new StringBuilder();
        try (var in = new BufferedReader(new FileReader(file))) {
            var dataBuffer = new char[512];
            int charsRead;
            while ((charsRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                for (int i = 0; i < charsRead; i++) {
                    if (predicate.test(dataBuffer[i])) {
                        output.append(dataBuffer[i]);
                    }
                }
            }
        }
        return output.toString();
    }
}
