package com.myThreadPool;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "queueSize-noOfThreads")
    public Object[][] firstMethod(){
        return new Object[][] {{1, 10}, {3, 10}, {5, 5}};
    }
}
