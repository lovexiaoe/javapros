package net.zhaoyu.javapros.j2se.threads.basic.threadinterrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 每一个线程都具有的boolean标志。
 *
 * 线程调用interrupt方法时。只是将线程的中断状态设置为true
 * 如果线程是被阻塞状态（调用sleep或wait或join或lock.tryLock或wait），调用interrupt方法时，会抛出InterruptedException异常，
 * 并会清除Interrupt状态。
 *
 * 某些线程是如此重要，已至于处理完InterruptedException后继续执行。
 *
 * isInterrupted
 * 返回中断状态。 在阻塞状态下返回false。
 *
 * interrupted
 * 返回中断状态， 在阻塞状态下返回false。
 * 和isInterrupted方法类似，但是在返回的同时会清除interrupted状态，作用于Thread.currentThread()。
 */
public class ThreadInterrupt2 {
	public static void main(String[] args) throws InterruptedException {
//		interrupted();
		parkInterrupt();
		//正常打断。
//		nomalInterrupt();
		//sleep被打断
//		sleepInterrupt();
	}

	private static void interrupted() throws InterruptedException {
		System.out.println("A3: 初始化的interrupt status = "+Thread.currentThread().isInterrupted());
		Thread.currentThread().interrupt();
		System.out.println("A3: interrupt打断后的interrupt status = "+Thread.currentThread().isInterrupted());
		System.out.println("A3: 执行interrupted结果"+Thread.currentThread().interrupted());
		System.out.println("A3: 执行interrupted后的线程状态"+Thread.currentThread().isInterrupted());
	}

	private static void parkInterrupt() throws InterruptedException {
		System.out.println("A4-1: "+Thread.currentThread().isInterrupted());
		LockSupport.park();
		System.out.println("A4-2: "+Thread.currentThread().isInterrupted());
		LockSupport.unpark(Thread.currentThread());
		System.out.println("A4-3: "+Thread.currentThread().isInterrupted());
	}

	private static void sleepInterrupt() throws InterruptedException {
		/**
		 * 如果线程是被阻塞状态（调用sleep或wait或join或lock.tryLock或wait），调用interrupt方法时，会抛出InterruptedException异常，
		 * 并会清除Interrupt状态。
		 */
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				try {
					long number=1L;
					while (true){
						number++;
						System.out.println("A2: "+number);
						if (isInterrupted()){
							break;
						}
					}
					System.out.println("A2，interrupt status="+isInterrupted()); //初始化为false
					this.sleep(2000);
					System.out.println("A2");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("B2，interrupt status="+isInterrupted()); //false,
				}
			}

		};

		thread2.start();
		TimeUnit.MILLISECONDS.sleep(1000);
		thread2.interrupt();
	}

	private static void nomalInterrupt() throws InterruptedException {
		//正常中断,interrupt的常规调用，当thread1被打断后，跳出循环，停止运行。输出 “A1: 线程在运行中被打断”
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				try {
					long number=1L;
					System.out.println("A1，Init interrupt status="+isInterrupted()); //初始化为false
					while (true){
						number++;
						System.out.println("A1: "+number);
						if (isInterrupted()){
							System.out.println("A1: 线程在运行中被打断");
							break;
						}
					}

				} catch (Exception e) {
					System.out.println("B1");
				}
			}
		};
		thread1.start();
		TimeUnit.MILLISECONDS.sleep(2);
		thread1.interrupt();
	}
}
