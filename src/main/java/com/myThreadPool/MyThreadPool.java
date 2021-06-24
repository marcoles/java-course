package com.myThreadPool.app;

public class MyThreadPool {

    private final MyQueue<RunnableDelayPairing> queue;

    /**
     * Constructor of MyThreadPool
     * creates a queue which will hold Runnables with their delay and starts up threads
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
     * Submit methods adds the Runnable to the queue
     * @param task
     * @param delay
     * @throws InterruptedException
     */
    public void submit (Runnable task, int delay) throws InterruptedException {
        RunnableDelayPairing taskWithDelay = new RunnableDelayPairing(task, delay);
        queue.enqueue(taskWithDelay);
    }
    public void submit (Runnable task) throws InterruptedException {
        RunnableDelayPairing taskWithoutDelay = new RunnableDelayPairing(task);
        queue.enqueue(taskWithoutDelay);
    }
}