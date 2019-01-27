package com.test.juc;

public class CompareAndSwapTest {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int oldValue = cas.get();
                    boolean b = cas.compareAndSet(oldValue, (int) (Math.random() * 101));
                    System.out.println(b);
                }
            }).start();
        }
    }

}


class CompareAndSwap{

    /**
     * 模拟CAS算法
     */

    private int value;

    //获取内存制
    public synchronized int get(){
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int exceptedValue, int newValue) {
        int oldValue = value;
        if (oldValue == exceptedValue) {
            value = newValue;
        }

        return oldValue;
    }


    //设置
    public synchronized boolean compareAndSet(int exceptedValue, int newValue) {
        return exceptedValue == compareAndSwap(exceptedValue, newValue);
    }

}