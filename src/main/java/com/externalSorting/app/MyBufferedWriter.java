package com.externalSorting.app;

import java.io.*;

public class MyBufferedWriter extends BufferedWriter {

    public MyBufferedWriter(File outputFile) throws IOException {
        super(new OutputStreamWriter(new FileOutputStream(outputFile)));
    }
}
