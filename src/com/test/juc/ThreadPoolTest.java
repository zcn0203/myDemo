package com.test.juc;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {

        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        ThreadPoolDemo tpd = new ThreadPoolDemo();
/*
        //为线程池内的线程分配任务
        for (int i = 0; i < 10; i++) {
            pool.submit(tpd);
        }

        //结束线程
        pool.shutdown();*/

        //实现Callable
        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0 ;
                for (int i = 0; i < 100; i++) {
                    sum = sum + i + 1;
                }

                return sum;
            }
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();

    }


}

class ThreadPoolDemo implements Runnable{

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+":"+(i+1));
        }

    }
}
