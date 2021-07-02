package com.myThreadPool;

import com.myThreadPool.bankAccountApp.AddOperation;
import com.myThreadPool.bankAccountApp.CustomBankAccount;
import com.myThreadPool.data_model.ThreadPoolTestData;
import org.junit.Assert;
import org.testng.annotations.Test;

public class MyThreadPoolTests {

    @Test (dataProvider = "queueSize-noOfThreads", dataProviderClass = DataProviders.class)
    public void addToTheSameAccountTenTimes (ThreadPoolTestData testData) throws InterruptedException {
        MyThreadPool threadPool = new MyThreadPool(testData.getQueueSize(), testData.getThreadsNumber());
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
        Thread.sleep(40000);
        Assert.assertEquals("The final account balance is not correct", expectedResult, account.getBalance(), 0.00001);
    }


}
