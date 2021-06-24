package com.myThreadPool;

/**
 * This is a wrapper class to contain both the Runnable and its delay, after which it is supposed to execute
 */
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

    public Runnable getTask() {
        return task;
    }

    public long getDelay() {
        return delay;
    }
}