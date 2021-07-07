package com.myThreadPool;

import java.util.ArrayList;
import java.util.List;

public class MyThreadPool {

    private final MyQueue<RunnableDelayPairing> queue;
    private final List<Thread> threadList = new ArrayList<>();

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
            threadList.add(thread);
        }
    }

    public void startAll() {
        for (Thread thread: threadList) {
            thread.start();
        }
    }

    public void shutdownWhenAllWaiting() {
        boolean shutdownFlag = false;
        try {
            while(!shutdownFlag) {
                if(checkIfWaiting()) {
                    Thread.sleep(3000); // double check
                    if(checkIfWaiting()) {
                        shutdownFlag = true;
                    }
                }
            }
            for (Thread thread: threadList) {
                thread.interrupt();
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfWaiting() {
        boolean flag = true;
        for (Thread thread: threadList) {
            if(!thread.getState().toString().equals("WAITING")) {
                flag = false;
            }
        }
        return flag;
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