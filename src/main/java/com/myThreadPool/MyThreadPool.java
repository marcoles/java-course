package com.myThreadPool;

public class MyThreadPool {

    private final MyQueue<RunnableDelayPairing> queue;

    /**
     * Constructor of MyThreadPool
     * Creates a queue which will hold Runnables with their delay
     * Creates and starts up threads
     *
     * @param queueSize
     * Size of the queue which will contain the Runnables
     * @param numberOfThreads
     * Number of threads available in the thread pool
     */
    public MyThreadPool (int queueSize, int numberOfThreads) {
        this.queue = new MyQueue<>(queueSize);
        String threadName;
        TaskExecutor taskExecutor;
        for (int cnt = 1; cnt <= numberOfThreads; cnt++) {
            threadName = String.format("Thread-%d", cnt);
            taskExecutor = new TaskExecutor(queue);
            Thread thread = new Thread(taskExecutor, threadName);
            thread.start();
        }
    }

    /**
     * Submit method adds the Runnable with its delay to the queue using a wrapper class RunnableDelayPairing
     *
     * @param task
     * Runnable to be added to the queue
     * @param delay
     * Delay after which the Runnable will be executed
     */
    public void submit (Runnable task, int delay) throws InterruptedException {
        RunnableDelayPairing taskWithDelay = new RunnableDelayPairing(task, delay);
        queue.enqueue(taskWithDelay);
    }

    /**
     * Submit method adds a Runnable without a delay to the queue
     *
     * @param task
     * Runnable to be added to the queue
     */
    public void submit (Runnable task) throws InterruptedException {
        RunnableDelayPairing taskWithoutDelay = new RunnableDelayPairing(task);
        queue.enqueue(taskWithoutDelay);
    }
}