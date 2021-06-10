package com.externalSorting.app;

import java.io.*;
import java.util.*;


public class ExternalSortingRunner {

    private final String originalFile;
    private final String outputFolder;

    public ExternalSortingRunner(String originalFile, String outputFile) {
        this.originalFile = originalFile;
        this.outputFolder = outputFile;
    }

    protected void externalSort() {

        System.out.println(outputFolder + "/tmpdir");
        File newDirectory = new File("src/main/resources/tmpdir/");
        List<File> files = readAndSplitData("src/main/resources/SmallLibrary.txt", newDirectory);

        System.out.println(outputFolder + "/Output.txt");
        mergeFiles(files, new File("src/main/resources/SmallLibraryOutput.txt"), comparator);
        for (File file : files) {
            file.delete();
        }
        newDirectory.delete();
    }
    /**
    CHANGE_FILE
    Maximum number of lines of text that can be put into a single temporary file
    */
    private static final int TMP_FILE_MAXSIZE = 24000000; // 24mb

    /**
    Defines a default string comparator
    */
    private static final Comparator<String> comparator = (o1, o2) -> o1.compareTo(o2);

    /**
    Reads data from a single text file and splits it into a number of temporary text files
    containing lists of alphabetically sorted words

    *param* fileName
    Path to original single text file to be read from

    *param* directory
    Path of the directory which will contain temporary text files with lists of sorted words

    *return*
    Returns a list of created temporary files containing sorted data from original file
    */
    private static List<File> readAndSplitData(String filePath, File directoryPath) {
        List<String> buffer = new ArrayList<>();
        List<File> files = new ArrayList<>();

        try (BufferedReader reader = new MyBufferedReader(filePath)) {
            int currentEstimatedFileSize = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()){
                List<String> splitLine = Arrays.asList(formatString(line));
                for (String s: splitLine) {
                    currentEstimatedFileSize += s.length() + 1;
                }
                buffer.addAll(splitLine);
                if (currentEstimatedFileSize >= TMP_FILE_MAXSIZE) {
                    currentEstimatedFileSize = 0;
                    files.add(sortAndWriteData(buffer, directoryPath));
                    buffer.clear();
                }
            }
            files.add(sortAndWriteData(buffer, directoryPath)); // write remaining data
            buffer.clear();

        } catch(IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        return files;
    }

    /**
    Sorts a list of strings using a default comparator
    Then writes the list to a new temporary text file created in the given directory

    *param* buffer
    List of strings to be sorted and written to the temporary file

    *param* directory
    Path of the directory which will contain temporary text files with lists of sorted words

    *return*
    Returns the created temporary file
    */
    private static File sortAndWriteData(List<String> buffer, File directory) throws IOException {
        buffer.sort(comparator);
        File newFile = File.createTempFile("splitData", "flatFile", directory);
        try (BufferedWriter writer = new MyBufferedWriter(newFile)) {
            for (String s : buffer) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        return newFile;
    }

    /**
    Creates InputBuffer instances for every temporary text file and opens their BufferedReaders

    *param* files
    List of files that will be used to create InputBuffer instances

    *return*
    Returns a list of created InputBuffer instances
    */
    private static ArrayList<InputBuffer> convertFilesToInputBuffers(List<File> files) {
        ArrayList<InputBuffer> buffers = new ArrayList<>();
        for (File file : files) {
            try {
                BufferedReader reader = new MyBufferedReader(file);
                InputBuffer buffer = new InputBuffer(reader);
                buffers.add(buffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
        return buffers;
    }

    /**
    Takes a list of text files and merges them into a single text file while keeping an alphabetical order of words

    *param* files
    List of temporary text files containing data from the original file

    *param* outputFile
    Path to the file that will contain all the data from the original text file, sorted into a list of sorted words

    *param* cmp
    Comparator used as a base of the comparator in priority queue
    */
    private static void mergeFiles(List<File> files, File outputFile, Comparator<String> cmp) {
        ArrayList<InputBuffer> buffers = convertFilesToInputBuffers(files);
        PriorityQueue<InputBuffer> priorityQueue = new PriorityQueue<>(10,
                                                            (o1, o2) -> cmp.compare(o1.getCurrentString(), o2.getCurrentString()));

        try (BufferedWriter writer = new MyBufferedWriter(outputFile)){

            for (InputBuffer buffer : buffers) {
                if (!buffer.isEmpty()) {
                    priorityQueue.add(buffer);
                }
            }
            while(priorityQueue.size() > 0) {
                InputBuffer buffer = priorityQueue.poll();
                String string = buffer.pop();
                writer.write(string);
                writer.newLine();
                if (buffer.isEmpty()) {
                    buffer.close();
                } else {
                    priorityQueue.add(buffer);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // closing readers from InputBuffer instances
        try {
            for (InputBuffer buffer : buffers) {
                buffer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }


    }

    /**
    Takes a line of text and formats it by:
        - changing all characters to lower case
        - removing punctuation characters
        - splitting it into an array of words

     *param* inputString
     Line of text to be formatted
     */
    private static String[] formatString(String inputString) {
        return inputString
                .toLowerCase()
                .replaceAll("\\p{Punct}", "")
                .split("\\s+");
    }

}
