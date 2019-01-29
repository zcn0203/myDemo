package com.test.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用线程循环打印ABC
 */
public class LockABCTest {

    private int num = 1;

    Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public static void main(String[] args) {
        LockABCTest test = new LockABCTest();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                test.loopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                test.loopB(i);
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                test.loopC(i);
            }
        }, "C").start();

    }

    public void loopA(int temp) {

        lock.lock();
        try {
            if (num != 1) {
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                }
            }

            System.out.print(Thread.currentThread().getName() + temp + "\t");
            num = 2;
            condition2.signal();


        } finally {
            lock.unlock();
        }
    }

    public void loopB(int temp) {

        lock.lock();
        try {
            if (num != 2) {
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.print(Thread.currentThread().getName() + temp + "\t");
            num = 3;
            condition3.signal();


        } finally {
            lock.unlock();
        }
    }

    public void loopC(int temp) {

        lock.lock();
        try {
            if (num != 3) {
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.print(Thread.currentThread().getName() + temp + "\t");
            num = 1;
            condition1.signal();


        } finally {
            lock.unlock();
        }
    }
}


