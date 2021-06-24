package com.externalSorting.app;

import java.io.*;
import java.util.*;


public class ExternalSortingRunner {

    private final PathsData paths;

    /**
     * Constructor
     *
     * @param paths
     * Contains paths to the original file and the output folder
     */
    public ExternalSortingRunner(PathsData paths) {
        this.paths = paths;
    }

    /**
     AVAILABLE_MEMORY
     Used to calculate maximum size of temporary files
     */
    private static final long AVAILABLE_MEMORY = Runtime.getRuntime().freeMemory();

    /**
     TMP_FILE_MAXSIZE
     Maximum number of bytes of a single temporary file. The value of this variable controls memory usage.
     */
    private static final long TMP_FILE_MAXSIZE = AVAILABLE_MEMORY / 10; // around 22MB for -Xmx256m

    /**
     Defines a default string comparator
     */
    private static final Comparator<String> comparator = Comparator.naturalOrder();

    /**
     * This method is meant to be called from main function.
     * It is responsible for:
     *  creating a temporary folder which will hold data files
     *  calling readAndSplitData and mergeFiles
     *  deleting temporary folder and files after the process is finished
     */
    public void externalSort() {
        File newDirectory = new File(String.format("%s/tmpdir", paths.getOutputFolder()));
        newDirectory.mkdir();
        List<File> files = readAndSplitData(paths.getOriginalFile(), newDirectory);
        mergeFiles(files, new File( String.format("%s/Output.txt", paths.getOutputFolder())));
        for (File file : files) {
            file.delete();
        }
        newDirectory.delete();
    }

    /**
     * Reads data from a single text file and splits it into a number of temporary text files
     * containing lists of alphabetically sorted words
     *
     * @param filePath
     * Path to the text file from which data will be read
     * @param directoryPath
     * Path of the directory which will contain temporary text files with lists of sorted words
     * @return
     * Returns a list of created temporary files containing sorted data from original text file
     */
    private List<File> readAndSplitData(String filePath, File directoryPath) {
        List<String> buffer = new ArrayList<>();
        List<File> files = new ArrayList<>();
        int NEWLINE_CHAR_SIZE = 2;

        try (BufferedReader reader = new MyBufferedReader(filePath)) {
            int currentEstimatedFileSize = 0;
            List<String> splitLine;
            for (String line = reader.readLine(); line != null; line = reader.readLine()){
                splitLine = Arrays.asList(formatString(line));
                for (String s: splitLine) {
                    currentEstimatedFileSize += s.getBytes().length + NEWLINE_CHAR_SIZE;
                }
                buffer.addAll(splitLine);

                if (currentEstimatedFileSize >= TMP_FILE_MAXSIZE) {
                    currentEstimatedFileSize = 0;
                    files.add(sortAndWriteData(buffer, directoryPath));
                    buffer.clear();
                    System.out.println("MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
                }
            }
            files.add(sortAndWriteData(buffer, directoryPath)); // write remaining data
            buffer.clear();

        } catch(IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    /**
     * Sorts a list of strings using a default comparator
     * Then writes the list to a new temporary text file created in the given directory
     *
     * @param buffer
     * List of strings to be sorted and written to the temporary file
     * @param directory
     * Path of the directory which will contain temporary text files with lists of sorted words
     * @return
     * Returns the created temporary file
     */
    private File sortAndWriteData(List<String> buffer, File directory) throws IOException {
        buffer.sort(comparator);
        File newFile = File.createTempFile("splitData", "flatFile", directory);
        try (BufferedWriter writer = new MyBufferedWriter(newFile)) {
            for (String s : buffer) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * Creates InputBuffer instances for every temporary text file
     *
     * @param files
     * List of files that will be used to create InputBuffer instances
     * @return
     * Returns a list of created InputBuffer instances
     */
    private ArrayList<InputBuffer> convertFilesToInputBuffers(List<File> files) {
        ArrayList<InputBuffer> buffers = new ArrayList<>();
        for (File file : files) {
            try {
                BufferedReader reader = new MyBufferedReader(file);
                InputBuffer buffer = new InputBuffer(reader);
                buffers.add(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffers;
    }

    /**
     * Takes a list of temporary text files and merges them into a single text file
     * while keeping an alphabetical order of words using priority queue
     *
     * @param files
     * List of temporary text files
     * @param outputFile
     * Path to the file that will contain all the merged data from temporary files
     */
    private void mergeFiles(List<File> files, File outputFile) {
        ArrayList<InputBuffer> buffers = convertFilesToInputBuffers(files);
        PriorityQueue<InputBuffer> priorityQueue = new PriorityQueue<>(10,
                                                            (o1, o2) -> ExternalSortingRunner.comparator.compare(o1.getCurrentString(), o2.getCurrentString()));

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        // closing readers from InputBuffer instances
        try {
            for (InputBuffer buffer : buffers) {
                buffer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a line of text and formats it by:
     *   changing all characters to lower case
     *   removing punctuation characters
     *   splitting it into an array of words
     *
     * @param inputString
     * Line of text to be formatted
     * @return
     * Returns an array of strings containing all the words from inputString
     */
    private String[] formatString(String inputString) {
        return inputString
                .toLowerCase()
                .replaceAll("\\p{Punct}", "")
                .split("\\s+");
    }

}
