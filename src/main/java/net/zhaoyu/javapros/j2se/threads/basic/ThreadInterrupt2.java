package net.zhaoyu.javapros.j2se.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在线程的run方法中如果出现未捕获的异常，或者run方法执行完毕，线程将会终止。
 * 在java的早期版本中，提供了stop方法和suspend方法，
 * 直接终止线程，释放线程所获的资源，但是在释放过程中会造成对象状态不一致，从而使程序进入未知的境地，已被弃用。
 *
 * 当一个线程调用interrupt方法时。这个方法不会中断线程，只是将线程的中断状态置位，
 * 中断状态为true，这是每一个线程都具有的boolean标志。
 *
 * 当一个被阻塞的线程（调用sleep或wait）调用interrupt方法时，阻塞将会被InterruptedException异常中断。
 * 当一个未阻塞的线程调用interrupt方法时，只是标记了中断状态, 线程的执行并不会受到影响，被设置中断状态的线程可以决定如何响应中断。
 *
 * 某些线程是如此重要，已至于处理完InterruptedException后继续执行。
 *
 * void interrupt方法，
 * 在某些阻塞情况下当前线程会抛出异常，并且清除interrupt状态（如wait，sleep，join —— InterruptedException）
 * 所以在有sleep，wait,join等方法的程序中不需要进行isInterrupt检查，相反，需要捕获InterruptException。
 * 而某些阻塞情况不会抛出。具体参考源代码。
 * 其他情况，将中断状态设置为true。
 *
 * isInterrupted
 * 返回中断状态。 在阻塞状态下返回false。
 *
 * static boolean interrupted
 * 返回中断状态， 在阻塞状态下返回false。
 * 和isInterrupted方法类似，但是在返回的同时会清除interrupted状态，如果被两次成功调用，第二次返回为false。
 */
public class ThreadInterrupt2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//正常中断,interrupt的常规调用
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				try {
					long number=1L;
					while (true){
						number++;
						System.out.println("A1: "+number);
						if (isInterrupted()){
							System.out.println("The 线程在运行中被打断");
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

		// 在线程sleep状态下进行中断
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				try {
					this.sleep(2000);
					System.out.println("A2");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("B2");
				}
			}

		};

		thread2.start();
		thread2.interrupt();

		// 在线程wait状态下进行中断,其中wait()没有在同步块中
		Thread thread3 = new Thread() {
			@Override
			public void run() {
				try {
					this.wait(2000);
					System.out.println("A3");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("B3");
				}
			}

		};

		thread3.start();
		thread3.interrupt();

		// 在线程wait状态下进行中断,其中wait()在同步块中，
		Thread thread4 = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						this.wait(2000);
						System.out.println("A4");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("B4");
				}
			}

		};

		thread4.start();
		thread4.interrupt();


		//在阻塞状态下进行中断，阻塞的线程thread5会抛出异常
		final Lock lock=new ReentrantLock();
		try {
			if (lock.tryLock(2000, TimeUnit.MILLISECONDS)){
				Thread.sleep(3000);//这里主线程获取3秒锁
				lock.unlock();
			}
		} catch (InterruptedException e) {
			//打断处理
		}

		TryLock tryLoc=new TryLock(lock);
		Thread thread5=new Thread(tryLoc);
		thread5.start();
		thread5.interrupt();
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