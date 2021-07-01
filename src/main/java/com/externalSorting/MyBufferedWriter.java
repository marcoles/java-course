package com.externalSorting;

import java.io.*;

/**
 * Provides a constructor to create a buffered writer on a File object
 */
public class MyBufferedWriter extends BufferedWriter {

    public MyBufferedWriter(File outputFile) throws IOException {
        super(new OutputStreamWriter(new FileOutputStream(outputFile)));
    }
}
