package com.myThreadPool.app;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue<T> {

    private final Queue<T> queue = new LinkedList<>();

    private final int EMPTY = 0;
    private int MAX_TASK_IN_QUEUE = -1;

    public MyQueue(int size){
        this.MAX_TASK_IN_QUEUE = size;
    }

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