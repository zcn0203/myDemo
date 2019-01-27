package com.test.juc;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    /**
     * 一、i++的原子性问题：i++的操作实际上分为三个步骤“读-改-写”
     * int i = 10;
     * i = i++;// i == 10
     * 内存中
     * int temp = i;
     * i = i + 1;
     * i = temp;
     * 二、原子变量：jdk1.5之后 java.util.concurrent.atomic包下提供了常用的原子变量
     * 1.volatile保证内存可见性
     * 2.CAS（Compare-And-Swap）算法保证数据的原子性
     * CAS算法是硬件对于并发操作中共享数据的支持
     * 包括三个操作数：
     * 内存值V
     * 预估值A
     * 更新值B
     * 当且仅当内存值等于预估值时，将内存值V更新为B，否则不进行任何操作
     */

    public static void main(String[] args) {

        AtomicDemo t = new AtomicDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(t).start();
        }

    }

}

class AtomicDemo implements Runnable {

    //private volatile int serialNumber = 0;
    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber(){
        return serialNumber.getAndIncrement();
    }
}
