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
 *
 * isInterrupted(boolean ClearInterrupted);
 * 返回中断状态。 在阻塞状态下返回false。ClearInterrupted 是否同时清除打断状态。
 *
 * isInterrupted()
 * =isInterrupted(false)，不清除打断状态。实例方法
 *
 * interrupted()
 * =currentThread().isInterrupted(true)，会清除interrupted状态。是静态方法，作用于当前线程。
 * 返回中断状态， 在阻塞状态下返回false。
 */
public class ThreadInterrupt2 {
	public static void main(String[] args) throws InterruptedException {
//		interrupted();
//		interruptedWaitTest();
//		interruptedBeforeWaitTest();
		parkTest();
//		interruptedParkTest();
	}

	/**
	 * 对 wait中的线程调用interrupt方法，线程会抛出异常。
	 * 输出如下
	 * Thread-0 开始wait
	 * Thread-0 等待状态中检测打断状态:false
	 * Thread-0 打断后立即检测打断状态:true
	 * Thread-0 打断中断了wait状态，抛出异常后，interruption状态会被清除。
	 * Thread-0 wait结束，后续执行
	 * Thread-0 end
	 * Thread-0 打断2s后检测打断状态:false
	 */
	private static void interruptedWaitTest() throws InterruptedException {
		Integer lock=1;
		Thread t_son = new Thread(() -> {
			String tName = Thread.currentThread().getName();
			synchronized (lock) {
				System.out.println(tName +" 开始wait");
				try {
					lock.wait();
				} catch (InterruptedException e) { //wait如果被打断，那么会抛出异常，并清除interruption状态
					System.out.println(tName +" 打断中断了wait状态，抛出异常后，interruption状态会被清除。");
				}
				System.out.println(tName+" 当前的打断状态为："+Thread.currentThread().isInterrupted());
				while (!Thread.interrupted()) {
				}
				System.out.println(tName +" end");
			}

		});
		t_son.start();
		TimeUnit.SECONDS.sleep(2);
		System.out.println(t_son.getName()+" 等待状态中检测打断状态:"+t_son.isInterrupted());
		t_son.interrupt();
		System.out.println(t_son.getName()+" 打断后立即检测打断状态:"+t_son.isInterrupted());
		TimeUnit.SECONDS.sleep(2);
		t_son.interrupt();//结束线程。
		t_son.join();
	}

	/**
	 * 先对线程进行打断，然后执行wait,也会抛出异常。
	 * 输出如下
	 * Thread-0 打断后立即检测打断状态:true
	 * Thread-0 开始wait
	 * Thread-0 打断中断了wait状态，抛出异常后，interruption状态会被清除。
	 * Thread-0 end
	 */
	private static void interruptedBeforeWaitTest() throws InterruptedException {
		Integer lock=1;
		Thread t_son = new Thread(() -> {
			String tName = Thread.currentThread().getName();
			while (!Thread.currentThread().isInterrupted()) {
			}
			synchronized (lock) {
				System.out.println(tName +" 开始wait");
				try {
					lock.wait();
				} catch (InterruptedException e) { //wait如果被打断，那么会抛出异常，并清除interruption状态
					System.out.println(tName +" 打断中断了wait状态，抛出异常后，interruption状态会被清除。");
				}
			}
			System.out.println(tName +" end");

		});
		t_son.start();
		t_son.interrupt();
		System.out.println(t_son.getName()+" 打断后立即检测打断状态:"+t_son.isInterrupted());
		TimeUnit.SECONDS.sleep(2);
		synchronized (lock) {
			lock.notify();//唤醒t_son线程，让他结束。
		}
		t_son.join();
	}


	/**
	 * 正常调用interrupt方法，对线程设置一个打断状态，不会做其他动作。
	 *
	 * @throws InterruptedException
	 */
	private static void interrupted() throws InterruptedException {
		System.out.println("A3: 初始化的interrupt status = "+Thread.currentThread().isInterrupted());//false
		Thread.currentThread().interrupt();
		System.out.println("A3: interrupt打断后的interrupt status = "+Thread.currentThread().isInterrupted());//true
		System.out.println("A3: 执行interrupted结果"+Thread.interrupted());//true
		System.out.println("A3: 执行interrupted后的线程状态"+Thread.currentThread().isInterrupted());//false
	}

	/**
	 * interrupt会打断park状态，且park结束后不会清除interrupt状态。
	 * 输出如下
	 * Thread-1 打断后立即检测打断状态:true
	 * Thread-1 开始park
	 * Thread-1 interrupt导致park结束后打断状态为：true
	 * Thread-1 结束park
	 */
	private static void interruptedParkTest() throws InterruptedException {
		Integer lock=1;
		Thread t_son = new Thread(() -> {
			String tName = Thread.currentThread().getName();
			while (!Thread.currentThread().isInterrupted()) {
			}
			System.out.println(tName +" 开始park");
			LockSupport.park();
			System.out.println(tName+" interrupt导致park结束后打断状态为："+Thread.currentThread().isInterrupted());
			System.out.println(tName+" 结束park");

		});
		t_son.start();
		t_son.interrupt();
		System.out.println(t_son.getName()+" 打断后立即检测打断状态:"+t_son.isInterrupted());
		TimeUnit.SECONDS.sleep(2);
		t_son.join();
	}

	/**
	 * park禁用线程调度当前线程，等待一个许可可用。如果许可被释放，那么会立即恢复。
	 * 释放许可有下面3中情况：
	 * 1.其他线程调用了当前线程的unPark
	 * 2.当前线程被interrupt打断
	 * 3.莫名的返回。
	 *
	 * unpark释放一个许可，重复调用只会释放一个许可，和park没有先后顺序，再等待park消费。
	 */
	private static void parkTest() throws InterruptedException {
		Integer lock=1;
		Thread t_son = new Thread(() -> {
			String tName = Thread.currentThread().getName();
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(tName +" 开始park");
			LockSupport.park();
			System.out.println(tName+" 结束park");
		});
		t_son.start();
		LockSupport.unpark(t_son); //unpark可以先于park调用。
		System.out.println(t_son.getName()+" 调用了unPark");
		t_son.join();
	}
}
