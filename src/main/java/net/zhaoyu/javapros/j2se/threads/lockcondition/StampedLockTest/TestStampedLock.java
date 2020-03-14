package net.zhaoyu.javapros.j2se.threads.lockcondition.StampedLockTest;

import java.util.concurrent.locks.StampedLock;

/**
 *  StampedLock有Write,Reade,OptimisticReade三种模式。
 *  Write：排它锁；
 *  Reader，非排它锁，可以和其他Reade,OptimisticReade共享，会阻塞；
 *  OptimisticReade，不会控制阻塞。如果被其他write线程控制了锁，则通过tryOptimisticRead方法返回0，否则返回其他值，
 *  使用validate进行验证。
 *
 *  当一个线程获取写锁时，Reader和OptimisticReader都不能访问到数据。此时，readLock()方法会挂起，OptimisticReader调用
 *  validate()方法会返回false,tryOptimisticRead()会返回0。当写锁释放时，Reader和OptimisticReader都能访问共享数据。
 *
 *  StampedLock并不是一个重入锁，所以一个线程得到的锁，可以在另一个线程中释放。
 */
public class TestStampedLock {
    public static void main(String[] args) {
        Position position=new Position();
        StampedLock lock=new StampedLock();
        Thread threadWriter=new Thread(new Writer(position,lock));
        Thread threadReader=new Thread(new Reader(position, lock));
        Thread threadOptReader=new Thread(new OptimisticReader
                (position, lock));
        threadWriter.start();
        threadReader.start();
        threadOptReader.start();
        try {
            threadWriter.join();
            threadReader.join();
            threadOptReader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
