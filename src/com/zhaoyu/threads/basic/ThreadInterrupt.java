package com.zhaoyu.threads.basic;

/**
 * 在线程的run方法中如果出现未捕获的异常，或者run方法执行完毕，线程将会终止。
 * 在java的早期版本中，提供了stop方法和suspend方法，
 * 直接终止线程，释放线程所获的资源，但是在释放过程中会造成对象状态不一致，从而使程序进入未知的境地，已被弃用。
 *
 * 当一个线程调用interrupt方法时。这个方法不会中断线程，只是将线程的中断状态置位，
 * 中断状态为true，这是每一个线程都具有的boolean标志。
 *
 * 首先要弄清楚中断状态是否被置位，如果线程被阻塞，就无法检测中断状态。
 *
 * 当一个被阻塞的线程（调用sleep或wait）调用interrupt方法时，阻塞将会被InterruptedException异常中断。
 * 当一个未阻塞的线程调用interrupt方法时，只是标记了中断状态, 线程的执行并不会受到影响，被设置中断状态的线程可以决定如何响应中断。
 * 某些线程是如此重要，已至于处理完InterruptedException后继续执行。
 *
 * void interrupt方法，如果目前线程被一个sleep调用阻塞，抛出InterruptedException异常。
 * 否则，将中断状态设置为true。
 *
 * isInterrupted只是返回中断状态，不会改变状态 。
 *
 * static boolean interrupted
 * 测试当前线程是否被中断，这一调用会产生副作用，它将当前线程的中断状态设置为false。
 *
 * @author xiaoE
 *
 */
public class ThreadInterrupt {
	public static void main(String[] args) {
		// 线程中断状态测试
		interruptTest();

		ThreadRunTest r1 = new ThreadRunTest();
		Thread t1 = new Thread(r1);
		t1.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// false
		System.out.println("主线程的打断状态：" + Thread.currentThread().isInterrupted());
		System.out.println(Thread.interrupted());
		// 在阻塞状态下检查打断状态会返回false。
		System.out.println("t1运行打断前的状态：" + t1.isInterrupted());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 *
		 */
		t1.interrupt();
		// false
		System.out.println("t1运行打断后的状态：" + t1.isInterrupted());
	}

	public static void interruptTest() {
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println("在线程中需要不时地检测interrupt标志。决断线程是否被中断。");
			return;
		}
	}

	/**
	 * 如果每次循环都调用sleep方法或者其它可中断方法（wait）,isInterrupted检测没有必要。
	 * 因为在中断状态下，调用sleep，线程不会休眠。并且它会清除中断状态并抛出一个InterruptException。
	 *
	 * 因此如果线程中调用了sleep或者wait方法，不需要进行isInterrupt检查，相反，需要捕获InterruptException。
	 */
	public static void sleepInterrupt() {
		try {
			int i = 0;
			while (i < 5) {
				Thread.sleep(1000);
				i++;

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {

		}
	}

}

/**
 * 线程测试类
 *
 * @author xiaoE
 *
 */
class ThreadRunTest implements Runnable {

	private int i = 0;

	@Override
	public void run() {
		System.out.println("测试线程开始");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		System.out.println("测试线程sleep了：" + 5 + "秒");
	}

	public int getI() {
		return i;
	}
}
