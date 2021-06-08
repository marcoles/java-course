package com.externalSorting.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ExternalSorting {
    /*
    CHANGE_FILE
    Maximum number of lines of text that can be put into a single temporary file
    */
    static final int CHANGE_FILE = 400000;

    /*
    Defines a default string comparator
    */
    public static Comparator<String> comparator = (o1, o2) -> o1.compareTo(o2);

    /*
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

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            int count = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()){
                List<String> splitLine = Arrays.asList(formatString(line));
                count++;
                // splitLine.removeIf(word -> word.isEmpty());
                buffer.addAll(splitLine);
                // buffer.remove("");  // remove empty strings in case of larger space between lines of text
                if (count >= CHANGE_FILE) {
                    count = 0;
                    files.add(sortAndWriteSplitData(buffer, directoryPath));
                    buffer.clear();
                }
            }
            files.add(sortAndWriteSplitData(buffer, directoryPath)); // write remaining data
            buffer.clear();

        } catch(IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        return files;
    }

    /*
    Sorts a list of strings using a default comparator
    Then writes the list to a new temporary text file created in the given directory

    *param* buffer
    List of strings to be sorted and written to the temporary file

    *param* directory
    Path of the directory which will contain temporary text files with lists of sorted words

    *return*
    Returns the created temporary file
    */
    private static File sortAndWriteSplitData(List<String> buffer, File directory) throws IOException {
        buffer.sort(comparator);
        File newFile = File.createTempFile("splitData", "flatFile", directory);
        OutputStream out = new FileOutputStream(newFile);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            for (String s : buffer) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        return newFile;
    }

    /*
    Creates InputBuffer instances for every temporary text file and opens their BufferedReaders

    *param* files
    List of files that will be used to create InputBuffer instances

    *return*
    Returns a list of created InputBuffer instances
    */
    private static ArrayList<InputBuffer> convertFilesToInputBuffers(List<File> files) {
        ArrayList<InputBuffer> buffers = new ArrayList<>();
        for (File file : files) {
            final int BUFFER_SIZE = 2000;
            try {
                BufferedReader reader = new BufferedReader(
                                                new InputStreamReader(
                                                        new FileInputStream(file)), BUFFER_SIZE);
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

    /*
    Takes a list of text files and merges them into a single text file while keeping an alphabetical order of words

    *param* files
    List of temporary text files containing data from the original file

    *param* outputFile
    Path to the file that will contain all the data from the original text file, sorted into a list of sorted words

    *param* cmp
    Comparator used as a base of the comparator in priority queue
    */
    private static void mergeFiles(List<File> files, File outputFile, Comparator<String> cmp) {
        // creating a list of InputBuffer instances from files
        ArrayList<InputBuffer> buffers = convertFilesToInputBuffers(files);

        // creating a priority queue which will be used to store words into the output file
        PriorityQueue<InputBuffer> priorityQueue = new PriorityQueue<>(10,
                                                            (o1, o2) -> cmp.compare(o1.getCurrentString(), o2.getCurrentString()));

        // try with resources for BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(
                                            new OutputStreamWriter(
                                                    new FileOutputStream(outputFile)))) {

            // putting all the InputBuffer instances into the priority queue
            for (InputBuffer buffer : buffers) {
                if (!buffer.isEmpty()) {
                    priorityQueue.add(buffer);
                }
            }
            // writing words from priority queue into the output file until the queue is empty
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

    /*
    Takes a line of text and formats it by:
        - changing all characters to lower case
        - removing punctuation characters
        - splitting it into an array of words

     *param* inputString
     Line of text to be formatted
     */
    public static String[] formatString(String inputString) {
        return inputString
                .toLowerCase()
                .replaceAll("\\p{Punct}", "")
                .split("\\s+");
    }


    public static void main(String[] args){
        File newDirectory = new File("src/main/resources/tmpdir/");
        List<File> files = readAndSplitData("src/main/resources/SmallLibrary.txt", newDirectory);
        mergeFiles(files, new File("src/main/resources/SmallLibraryOutput.txt"), comparator);
        for (File file : files) {
            file.delete();
        }
        // Kamil's comment
    }


}
