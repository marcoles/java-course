package com.externalSorting.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Provides constructors to create buffered readers from file path or File object
 */
public class MyBufferedReader extends BufferedReader {
    public MyBufferedReader(String filePath) throws IOException {
        super(Files.newBufferedReader(Paths.get(filePath)));
    }

    public MyBufferedReader(File file) throws IOException{
        super(new InputStreamReader(new FileInputStream(file)));
    }
}
