package com.myThreadPool;

import com.myThreadPool.bankAccountApp.AddOperation;
import com.myThreadPool.bankAccountApp.CustomBankAccount;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        MyThreadPool threadPool = new MyThreadPool(3,5);
        threadPool.startThreadPool();
        CustomBankAccount account = new CustomBankAccount();

        for(int taskNumber = 1 ; taskNumber <= 20; taskNumber++) {
            AddOperation task = new AddOperation(account);
            threadPool.submit(task, 10000);
        }

        threadPool.shutdownThreadPool();
        System.out.printf("Final account balance: %f\n", account.getBalance());

        double expectedResult = 0;
        for (int i = 1; i <= 20; i++) {
            expectedResult += 100;
            expectedResult += (expectedResult * 0.01);
            System.out.println(expectedResult);
        }


    }
}