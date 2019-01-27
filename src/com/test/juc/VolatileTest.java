package com.test.juc;

public class VolatileTest {
    /**
     * volatile关键字：
     * 当多个线程进行操作共享数据时，可以保证数据在内存中可见（多个线程在操作数据时，会首先操作缓存中的数据，然后操作主存）
     * 相对于synchronized是一种较为轻量级的同步策略
     *
     * volatile与synchronized的区别：
     * volatile不具备互斥性
     * volatile不能保证变量的原子性
     */

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        Thread t = new Thread(td);

        t.start();


        while (true) {
            if (td.getFlag()) {
                System.out.println("==================");
                break;
            }
        }

    }


}

class ThreadDemo implements Runnable {

    private volatile Boolean flag = false;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println("set flag");

        setFlag(true);

    }
}
