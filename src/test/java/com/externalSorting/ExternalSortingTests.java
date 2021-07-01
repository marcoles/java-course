package com.externalSorting;

import com.externalSorting.ExternalSortingRunner;
import com.externalSorting.PathsData;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;


public class ExternalSortingTests {

    @Test
    public void generalTest () {
        String testFile = "src/main/resources/Library.txt";
        String outputFolder = "src/main/resources";
        PathsData paths = new PathsData(testFile, outputFolder);
        ExternalSortingRunner runner = new ExternalSortingRunner(paths);

        runner.externalSort();

        try (BufferedReader reader = new BufferedReader(
                                            new InputStreamReader(
                                                    new FileInputStream(String.format("%s/Output.txt", outputFolder)))))
        {
            boolean flag = false;
            String tmpLine = "";
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.compareTo(tmpLine) < 0) {
                    flag = true;
                }
                tmpLine = line;
            }
            Assert.assertFalse("Somewhere in the output text file words are not sorted " +
                                            "according to the alphabetical order", flag);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
