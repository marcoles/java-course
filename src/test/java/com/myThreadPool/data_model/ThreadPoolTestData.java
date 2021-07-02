package com.myThreadPool.data_model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ThreadPoolTestData {
    private final int queueSize;
    private final int threadsNumber;
}
