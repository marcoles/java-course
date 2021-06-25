package com.myThreadPool;

import lombok.Getter;

/**
 * This is a wrapper class to contain both the Runnable and its delay, after which it is supposed to execute
 */
@Getter
public class RunnableDelayPairing {
    private final Runnable task;
    private final long delay;

    public RunnableDelayPairing(Runnable runnable, long delay) {
        this.task = runnable;
        this.delay = delay;
    }

    public RunnableDelayPairing(Runnable runnable) {
        this.task = runnable;
        this.delay = 0;
    }
}