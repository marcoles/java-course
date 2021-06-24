package com.myThreadPool;

import com.myThreadPool.bankAccountApp.AddOperation;
import com.myThreadPool.bankAccountApp.CustomBankAccount;
import org.junit.Assert;
import org.junit.Test;

public class MyThreadPoolTests {


    @Test
    public void addToTheSameAccountTenTimesUsingFiveThreads() throws InterruptedException {
        MyThreadPool threadPool = new MyThreadPool(3,5);
        CustomBankAccount account = new CustomBankAccount();

        for(int taskNumber = 1 ; taskNumber <= 10; taskNumber++) {
            AddOperation task = new AddOperation(account);
            threadPool.submit(task, 10000);
        }

        double expectedResult = 0;
        for (int i = 1; i <= 10; i++) {
            expectedResult += 100;
            expectedResult += (expectedResult * 0.01);
        }
        Thread.sleep(30000);
        Assert.assertEquals("The final account balance is not correct", expectedResult, account.getBalance(), 0.00001);
    }


}
