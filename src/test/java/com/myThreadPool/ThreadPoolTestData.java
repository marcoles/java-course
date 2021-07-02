package com.myThreadPool;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ThreadPoolTestData {
    private final int queueSize;
    private final int noOfThreads;
}
