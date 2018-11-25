package com.zhaoyu.threads.sychronized;

/**
 * synchronized加载方法上和synchronized(this)加在代码块上都是获得对象的monitor，如果把synchronized(this)记在整个方法体上，
 * 两者获得的效果是等效的 。
 * synchronized和synchronized(this)锁定时，其他线程可以访问非枷锁的代码。
 */
public class SychronizedTest {
    public synchronized  void testA(){
        System.out.println("synchronized Method testA");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End testA");
    }

    public  void testB(){
        synchronized(this){
            System.out.println("synchronized Method testB");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End testB");
        }
    }
    public void testC(){
        System.out.println("local Method testC");
    }

    public static void main(String[] args) {
        SychronizedTest test=new SychronizedTest();
        new Thread(() -> {
            test.testA();
        }).start();
        new Thread(() -> {
            test.testB();
        }).start();
        new Thread(() -> {
            test.testC();
        }).start();
    }
}
