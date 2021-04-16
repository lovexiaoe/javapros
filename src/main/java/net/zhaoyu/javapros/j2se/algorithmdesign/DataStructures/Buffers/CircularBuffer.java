package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Buffers;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 循环缓冲区
 * @Author: zhaoyu
 * @Date: 2020/12/17
 */
public class CircularBuffer {
    /** 存放数据的数组。 */
    private char[] data;
    /** 缓冲大小 */
    private int size;
    /** 当前读取位置 */
    private int readIndex;
    /** 当前写入位置 */
    private int writeIndex;
    /** 可以读取的数量 */
    private AtomicInteger readableCount = new AtomicInteger(0);

    public CircularBuffer(int size) {
        if (!isPowerOfTwo(size)) {
            throw new IllegalArgumentException();
        }
        data = new char[size];
        this.size = size;
    }

    /**
     * 判断i是否是2的n次方。
     */
    private boolean isPowerOfTwo(int i){
        return (i & (i-1))==0;
    }

    private int getTrueIndex(int i ){
        return i%size;
    }

    /** 读缓冲方法 */
    public Character readOutChar(){
        Character result=null;
        if (readableCount.get()>0) {
            result = data[getTrueIndex(readIndex++)];
            readableCount.decrementAndGet();
        }
        return result;
    }

    /** 写缓冲方法。 */
    public boolean WriteToBuffer(Character c){
        boolean result=false;
        if (readableCount.get() < size) {

            data[getTrueIndex(writeIndex++)]=c;
            readableCount.incrementAndGet();
            result=true;
        }
        return result;
    }

    private static class ReadWorker implements Runnable {
        CircularBuffer buffer;

        public ReadWorker(CircularBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            System.out.println("Read Buffer:");
            while (!Thread.interrupted()) {
                Character c=buffer.readOutChar();
                if (c != null) {
                    System.out.print(c);
                } else {
                    Thread.yield();
                    System.out.println("=====暂缓读取====");
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        System.out.println();
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    private static class WriteWorker implements Runnable {
        String alphabet="abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        CircularBuffer buffer;

        public WriteWorker(CircularBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (!buffer.WriteToBuffer(alphabet.charAt(random.nextInt(alphabet.length())))) {
                    Thread.yield();
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int bufferSize = 1024;
        CircularBuffer cb = new CircularBuffer(bufferSize);

        Thread writeThread = new Thread(new WriteWorker(cb));
        Thread readThread = new Thread(new ReadWorker(cb));
        writeThread.start();
        readThread.start();

        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        writeThread.interrupt();
        readThread.interrupt();
    }

}
