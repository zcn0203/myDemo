package com.test.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    public static void main(String[] args) {
        Ticket t = new Ticket();

        new Thread(t, "一号售票员").start();
        new Thread(t, "二号售票员").start();
        new Thread(t, "三号售票员").start();
    }

}

class Ticket implements Runnable {

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        while (true) {
            lock.lock();
            try{
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(Thread.currentThread().getName()+"卖出了票，余票:"+--tick);
                }
            }finally {
                lock.unlock();
            }
        }

    }
}
