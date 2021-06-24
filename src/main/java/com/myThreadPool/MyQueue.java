package com.myThreadPool;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue<T> {

    private final Queue<T> queue = new LinkedList<>();

    private final int EMPTY = 0;
    private int MAX_TASK_IN_QUEUE = -1;

    public MyQueue(int size){
        this.MAX_TASK_IN_QUEUE = size;
    }

    /**
     * The enqueue method is used to add an object to the queue.
     * If the queue is already full it will wait until notified.
     * If the queue is empty it will notify other threads.
     *
     * @param task
     * Task to be added to the queue
     */
    public synchronized void enqueue(T task)
            throws InterruptedException  {
        while(this.queue.size() == this.MAX_TASK_IN_QUEUE) {
            wait();
        }
        if(this.queue.size() == EMPTY) {
            notifyAll();
        }
        this.queue.offer(task);
    }

    /**
     * The dequeue method is used to remove an object from the queue.
     * If the queue is full it will notify other threads.
     * If the queue is empty it will wait until notified.
     *
     * @return
     * Returns the first object from the queue
     */
    public synchronized T dequeue()
            throws InterruptedException{
        while(this.queue.size() == EMPTY){
            wait();
        }
        if(this.queue.size() == this.MAX_TASK_IN_QUEUE){
            notifyAll();
        }
        return this.queue.poll();
    }
}