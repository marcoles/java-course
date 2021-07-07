package com.myThreadPool;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "queueSize-noOfThreads")
    public Object[][] firstMethod() {
        return new Object[][]{
                {
                        ThreadPoolTestData.builder()
                                .queueSize(1)
                                .noOfThreads(10)
                                .noOfTasks(10)
                                .delay(10000)
                                .build()
                },
                {
                        ThreadPoolTestData.builder()
                                .queueSize(3)
                                .noOfThreads(5)
                                .noOfTasks(10)
                                .delay(10000)
                                .build()
                },
                {
                        ThreadPoolTestData.builder()
                                .queueSize(5)
                                .noOfThreads(15)
                                .noOfTasks(20)
                                .delay(10000)
                                .build()
                }
        };
    }
}
