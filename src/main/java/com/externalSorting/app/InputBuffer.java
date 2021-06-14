package com.externalSorting.app;

import java.io.BufferedReader;
import java.io.IOException;

public class InputBuffer {

    private final BufferedReader reader;
    private String currentString;

    /**
     * Creates an InputBuffer instance with specified reader
     *
     * @param r
     * BufferedReader object
     */
    public InputBuffer(BufferedReader r) throws IOException {
        this.reader = r;
        reload();
    }

    /**
     * Reads a new String
     */
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

    /**
     * Gets current String and calls reload() to read a new one
     *
     * @return
     * Returns current String value
     */
    public String pop() throws IOException {
        String string = getCurrentString();
        reload();
        return string;
    }
}
