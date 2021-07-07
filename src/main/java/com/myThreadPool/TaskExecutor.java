package com.myThreadPool;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskExecutor implements Runnable{

    private final MyQueue<RunnableDelayPairing> queue;

    /**
     * The run methods contains a while loop which is responsible
     * for dequeueing runnables from the queue (if any are available) and starting them with a delay
     */
    @Override
    public void run(){
        try {
            while (true) {
                String name = Thread.currentThread().getName();
                RunnableDelayPairing taskWithDelay = queue.dequeue();
                Runnable task = taskWithDelay.getTask();
                System.out.printf("Waiting for response on %s...\n", name);
                Thread.sleep(taskWithDelay.getDelay());
                System.out.printf("Task Started by Thread : %s\n", name);
                task.run();
                System.out.printf("Task Finished by Thread : %s\n", name);
            }
        } catch (InterruptedException e) {
            String name = Thread.currentThread().getName();
            System.out.printf("Exiting Thread : %s\n", name);
            e.printStackTrace();
        }
    }
}