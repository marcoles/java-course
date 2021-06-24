package com.externalSorting.app;

/**
 * PathsData is used to store paths to the original text file and the output folder
 */
public class PathsData {

    private final String originalFile;
    private final String outputFolder;

    public PathsData(String originalFile, String outputFolder) {
        this.originalFile = originalFile;
        this.outputFolder = outputFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getOriginalFile() {
        return originalFile;
    }
}
