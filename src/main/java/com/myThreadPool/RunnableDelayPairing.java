package com.myThreadPool;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This is a wrapper class to contain both the Runnable and its delay, after which it is supposed to execute
 */
@Getter
@RequiredArgsConstructor
public class RunnableDelayPairing {
    private final Runnable task;
    private final long delay;

    public RunnableDelayPairing(Runnable runnable) {
        this.task = runnable;
        this.delay = 0;
    }
}