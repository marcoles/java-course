package com.externalSorting.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ExternalSorting {
    protected static final int CHANGE_FILE = 500000;
    private static int fileNumber = 0;

    public static List<String> readAndSplitData(String fileName) {
        List<String> buffer = new ArrayList<>();
        int count = 0;

        try (BufferedReader reader = Helper.openReader(fileName)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()){
                List<String> splitLine = Arrays.asList(formatString(line));
                count++;
                // splitLine.removeIf(word -> word.isEmpty());
                buffer.addAll(splitLine);
                // buffer.remove("");  // remove empty strings in case of larger space between lines of text
                if (count >= CHANGE_FILE) {
                    count = 0;
                    sortAndWriteSplitData(buffer);
                    buffer.clear();
                }
            }
        } catch(IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        writeData(buffer, "src/main/resources/SmallLibraryOutput" + fileNumber + ".txt"); // write remaining data
        return buffer;
    }

    private static void sortAndWriteSplitData(List<String> buffer) {
        sortAlphabetically(buffer);
        writeData(buffer, "src/main/resources/SmallLibraryOutput" + fileNumber + ".txt");
        fileNumber++;
    }

    public static void writeData(List<String> stringBuffer, String fileName) {
        try (BufferedWriter writer = Helper.openWriter(fileName)) {
            for (String s : stringBuffer) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    public static void sortAlphabetically(List<String> stringBuffer) {
        Collections.sort(stringBuffer);
    }
    public static String[] formatString(String inputString) {
        return inputString
                .toLowerCase()
                .replaceAll("\\p{Punct}", "")
                .split("\\s+");
    }

    public static void getFileSize(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            return;
        }

        System.out.println(file.length());
    }

    public static void getListSize(List<?> list) {
        System.out.println(list.size());
    }

    public static void main(String[] args){
        // getFileSize("src/main/resources/SmallLibrary.txt");
        /* List<String> text = */ readAndSplitData("src/main/resources/SmallLibrary.txt");
        // sortAlphabetically(text);
        //getListSize(text);
        // writeData(text,"src/main/resources/SmallLibraryOutput.txt");
    }
}
