package com.myThreadPool.app;

import com.myThreadPool.app.temporaryPackage.Adder;
import com.myThreadPool.app.temporaryPackage.CustomBankAccount;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThreadPool threadPool = new MyThreadPool(3,4);
        CustomBankAccount account = new CustomBankAccount();
        for(int taskNumber = 1 ; taskNumber <= 10; taskNumber++) {
            Adder task = new Adder(account);
            threadPool.submit(task, 10000);
        }
        Thread.sleep(40000);
        System.out.printf("Final account balance: %f\n", account.getBalance());

        double expectedResult = 0;
        for (int i = 1; i <= 10; i++) {
            expectedResult += 100;
            expectedResult += (expectedResult * 0.01);
            System.out.println(expectedResult);
        }
    }
}