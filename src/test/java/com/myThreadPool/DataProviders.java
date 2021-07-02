package com.myThreadPool;

import com.myThreadPool.data_model.ThreadPoolTestData;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "queueSize-noOfThreads")
    public Object[][] firstMethod(){
        return new Object[][] {
                { ThreadPoolTestData.builder()
                        .queueSize(1)
                        .threadsNumber(10)
                        .build()
                },
                { ThreadPoolTestData.builder()
                        .queueSize(3)
                        .threadsNumber(10)
                        .build()
                },
                { ThreadPoolTestData.builder()
                        .queueSize(5)
                        .threadsNumber(5)
                        .build()
                }
        };
    }
}
