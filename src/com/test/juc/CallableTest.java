package com.test.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable:创建线程的方式三：
 *  实现callable接口。相较于Runnable接口的方式，方法可以有返回值，并可以抛出异常
 *  执行Callable方式，需要FutureTask实现类的支持，用于接收结果运算。FutureTask时Future接口的实现类。
 */

public class CallableTest {

    public static void main(String[] args) {

        CallableDemo cd = new CallableDemo();
        FutureTask<Integer> result = new FutureTask(cd);

        new Thread(result).start();


        System.out.println("-----------");
        try {
            Integer sum = result.get();
            System.out.println("sum = " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}

class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }

        return sum;
    }
}
