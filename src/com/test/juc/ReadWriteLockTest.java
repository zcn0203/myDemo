package com.test.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    private int num = 1;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ReadWriteLockTest rd = new ReadWriteLockTest();


        new Thread(() -> {
            rd.set((int) (Math.random()*101));
        },"read").start();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                rd.get();
            },"write").start();
        }



    }

    public void get(){

        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+":"+num);
        }finally {
            lock.readLock().unlock();
        }

    }

    public void set(int num) {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName());
            this.num = num;
        }finally {
            lock.writeLock().unlock();
        }
    }


}

