package net.zhaoyu.javapros.j2se.threads.basic.threadinterrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在线程的run方法中如果出现未捕获的异常，或者run方法执行完毕，线程将会终止。
 *
 * 线程调用interrupt方法时。只是将线程的中断状态设置为true，这是每一个线程都具有的boolean标志。
 * 如果线程是被阻塞状态（调用sleep或wait或join），调用interrupt方法时，会清除Interrupt状态，并抛出InterruptedException异常。
 *
 * 如果线程是在lock.tryLock的阻塞状态，调用interrupt方法时，会抛出InterruptedException异常，并设置interrupt状态为true。
 *
 * 某些线程是如此重要，已至于处理完InterruptedException后继续执行。
 *
 * isInterrupted
 * 返回中断状态。 在阻塞状态下返回false。
 *
 * interrupted
 * 返回中断状态， 在阻塞状态下返回false。
 * 和isInterrupted方法类似，但是在返回的同时会清除interrupted状态。
 */
public class ThreadInterrupt2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//正常中断,interrupt的常规调用，当thread1被打断后，跳出循环，停止运行。输出 “A1: 线程在运行中被打断”
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				try {
					long number=1L;
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
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread1.interrupt();

		// 在线程sleep状态下进行中断，thread2发生InterruptedException异常，打印"B2，interrupt status=false"，
		// 因为中断后也清除了interrupt状态，
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				try {
					this.sleep(2000);
					System.out.println("A2");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("B2，interrupt status="+isInterrupted());
				}
			}

		};

		thread2.start();
		thread2.interrupt();


		// 在线程wait状态下进行中断,thread4发生InterruptedException异常，打印"B4"，并会清除中断状态。
		Thread thread4 = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						this.wait(2000);
						System.out.println("A4");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("B4");
				}
			}

		};

		thread4.start();
		thread4.interrupt();


		//thread5的lock.tryLock在阻塞状态下被中断，发生InterruptedException异常，打印"B5"，并设置中断状态为true
		final Lock lock=new ReentrantLock();
		try {
			//主线程获取lock
			if (lock.tryLock(2000, TimeUnit.MILLISECONDS)){
				System.out.println("Main thread get lock");
//				Thread.sleep(1000);//这里主线程获取1秒锁

				//thread5获取lock进入阻塞状态。
				TryLock tryLoc=new TryLock(lock);
				Thread thread5=new Thread(tryLoc);
				thread5.start();
				System.out.println("before interrupt thread5's interrupt flag ="+thread5.isInterrupted());//false
				thread5.interrupt(); //打断后抛出interruptedException异常。
				System.out.println("after interrupt thread5's interrupt flag ="+thread5.isInterrupted());//true

				Thread.sleep(1000);//这里主线程获取3秒锁
				lock.unlock();
			}
		} catch (InterruptedException e) {
			//打断处理
			System.out.println("Main thread be interrupted。");
		}

	}
}

class TryLock implements Runnable{
	private  Lock lock;
	public TryLock(Lock lock){
		this.lock=lock;
	}
	@Override
	public void run() {
		try {
			if (lock.tryLock(2000, TimeUnit.MILLISECONDS)){
				System.out.println("A5");
			}
			lock.unlock();
		} catch (InterruptedException e) {
			System.out.println("B5");
		}
	}
}