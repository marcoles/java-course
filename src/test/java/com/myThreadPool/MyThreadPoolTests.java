package com.myThreadPool;

import com.myThreadPool.bankAccountApp.AddOperation;
import com.myThreadPool.bankAccountApp.CustomBankAccount;
import org.testng.Assert;
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

        threadPool.startAll();
        for(int taskNumber = 1 ; taskNumber <= testData.getNoOfTasks(); taskNumber++) {
            AddOperation task = new AddOperation(account);
            threadPool.submit(task, testData.getDelay());
            listOfOperations.add(task);
        }
        threadPool.shutdownWhenAllWaiting();

        double expectedResult = BankOperationHelper.calculateExpectedBalance(listOfOperations);
        Assert.assertEquals(expectedResult, account.getBalance(), 0.00001, "The final account balance is not correct");
    }


}
