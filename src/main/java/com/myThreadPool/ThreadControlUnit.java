package com.myThreadPool;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ThreadControlUnit {
    private final List<Thread> threadList;

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

}
