package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public class MainClass {
    public static void main(String[] args) throws IOException {
        LoadFile loadFile = new LoadFile(new File("pom.xml"));
        Predicate<Character> startPredicate = ch -> ch < 0x080;
        System.out.println(loadFile.getContent(startPredicate));

        startPredicate = ch -> true;
        System.out.println(loadFile.getContent(startPredicate));
    }
}
