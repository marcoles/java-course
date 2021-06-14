package com.externalSorting.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        System.out.println("Type in the path to the file you want to sort:");
        String originalFile = input.nextLine();
        System.out.println("Type in the path to the output file folder:");
        String outputFile = input.nextLine();
        input.close();


        ExternalSortingRunner runner = new ExternalSortingRunner(originalFile, outputFile);
        runner.externalSort();
    }
}
