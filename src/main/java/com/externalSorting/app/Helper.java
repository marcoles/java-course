package com.externalSorting.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Helper {
    static public BufferedReader openReader(String fileName) throws IOException {
        return Files.newBufferedReader(Paths.get(fileName));
    }

    static public BufferedWriter openWriter(String fileName) throws IOException {
        return Files.newBufferedWriter(Paths.get(fileName));
    }

}





