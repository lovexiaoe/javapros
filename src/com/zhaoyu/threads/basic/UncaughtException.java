package com.zhaoyu.threads.basic;

/**
 * 线程的run方法不能抛出任何被检测的异常(如在run方法中抛出InterruptException会报错)，未检测的异常会导致线程终止。
 *
 * 不需要在任何catch子句中来处理可传播的异常。在线程死亡之前 ，异常被传递到一个用于未捕获异常的处理器中。
 * 处理器实现UncaughtExceptionHandler接口。这个接口只有一个方法。
 * void uncaughtException(Thread t,Throwable e);
 *
 * 可以通过 setUncaughtExceptionHandler方法为任何线程设置一个处理器。也可以使用
 * Thread的静态方法setDefaultUncaughtExceptionHandler为所有线程安装一个默认处理器。
 *
 * ThreadGroup类实现了UncaughtExceptionHandler接口，如果没有给线程指定默认的处理器。那么此时的处理器
 * 就是该线程的ThreadGroup对象。
 *
 * @author xiaoE
 *
 */
public class UncaughtException {

}

/**
 * 线程测试类
 *
 * @author xiaoE
 *
 */
class ThreadRunTest1 implements Runnable {

	private int i = 0;

	@Override
	public void run() throws InterruptedException {
		System.out.println("测试线程开始");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new InterruptedException();
		}
		System.out.println("测试线程sleep了：" + 5 + "秒");
	}

	public int getI() {
		return i;
	}
}
