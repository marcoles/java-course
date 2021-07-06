package com.myThreadPool;

import com.myThreadPool.bankAccountApp.AddOperation;
import com.myThreadPool.bankAccountApp.CustomBankAccount;
import org.junit.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Listeners (TestListener.class)
public class MyThreadPoolTests {

    @Test (dataProvider = "queueSize-noOfThreads", dataProviderClass = DataProviders.class)
    public void addToTheSameAccountMultipleTimes (ThreadPoolTestData testData) throws InterruptedException {
        MyThreadPool threadPool = new MyThreadPool(testData.getQueueSize(), testData.getNoOfThreads());
        CustomBankAccount account = new CustomBankAccount();
        List<AddOperation> listOfOperations = new ArrayList<>();

        for(int taskNumber = 1 ; taskNumber <= testData.getNoOfTasks(); taskNumber++) {
            AddOperation task = new AddOperation(account);
            threadPool.submit(task, testData.getDelay());
            listOfOperations.add(task);
        }


        double expectedResult = BankOperationHelper.calculateExpectedBalance(listOfOperations);
        Thread.sleep(60000);
        Assert.assertEquals("The final account balance is not correct", expectedResult, account.getBalance(), 0.00001);
    }


}
