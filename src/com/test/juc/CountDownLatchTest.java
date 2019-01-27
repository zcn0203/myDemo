package com.test.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁：CountDownLatch：在完成某些运算时，只有其他线程的运算全部完成，当前运算才能继续执行。
 *      Java5.0在java.util.concurrent包中提供了多种并发容器类来改进同步容器的性能
 *      CountDownLatch一个同步辅助类，在完成一组正在其他线程中执行的操作，它允许一个或多个线程一直等待
 *      闭锁可以延迟线程的进度直到其到达终止状态，闭锁可以用来确保某些活动直到其他活动都完成才能继续执行：
 *          确保某个计算在其需要的所有资源都被初始化后才能继续执行
 *          确保某个服务在其依赖的所有其他服务都已经启动之后才启动
 *          等待直到某个操作所有参与者都准备就绪再继续执行
 *
 */

public class CountDownLatchTest {

    public static void main(String[] args) {

        final CountDownLatch latch = new CountDownLatch(5);

        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();


        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
        }

        System.out.println("总计用时："+(System.currentTimeMillis()-start));


    }

}


class LatchDemo implements Runnable {

    private CountDownLatch latch;

    LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }


    @Override
    public void run() {

        synchronized(this){

            try{

                for (int i = 0; i <= 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }

            }finally {
                latch.countDown();
            }

        }

    }
}
