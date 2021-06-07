package com.externalSorting.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class InputBuffer {

    private BufferedReader reader;
    private String currentString;

    public InputBuffer(BufferedReader r) throws IOException {
        this.reader = r;
        reload();
    }

    private void reload() throws IOException {
        this.currentString = this.reader.readLine();
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public boolean isEmpty() {
        return this.currentString == null;
    }

    public String getCurrentString() {
        return this.currentString;
    }

    public String pop() throws IOException {
        String string = getCurrentString().toString();
        reload();
        return string;
    }
}
