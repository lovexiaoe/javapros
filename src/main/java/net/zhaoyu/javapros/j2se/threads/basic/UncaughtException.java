package net.zhaoyu.javapros.j2se.threads.basic;

/**
 * 线程的run方法不能抛出任何被检测的异常(如在run方法中抛出InterruptException会报错)，未检测的异常会导致线程终止。
 *
 * 不需要在任何catch子句中来传播异常。在线程死亡之前 ，异常被传递到一个用于未捕获异常的处理器中。
 *
 * 可以通过 setUncaughtExceptionHandler方法为任何线程设置一个处理器。也可以使用
 * Thread的静态方法setDefaultUncaughtExceptionHandler为所有线程安装一个默认处理器。
 *
 * 处理器实现UncaughtExceptionHandler接口。这个接口只有一个方法。
 * void uncaughtException(Thread t,Throwable e);
 *
 * 如果线程不设置任何的处理器，jvm会在异常抛出后结束线程，打印堆信息给console
 *
 *
 *
 * @author xiaoE
 *
 */
public class UncaughtException {
	public static void main(String[] args) {
		Task task=new Task();
		Thread thread=new Thread(task);
		//
		thread.setUncaughtExceptionHandler(new ExceptionHandler());
		thread.start();
		System.out.println("主程序运行完成");
	}

}

/**
 * run方法不能抛出一个异常
 */
//class ThreadRunTest1 implements Runnable {
//	@Override
//	public void run() throws InterruptedException {
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			throw new InterruptedException();
//		}
//	}
//
//}

class Task implements Runnable {
	@Override
	public void run() {
		int numero=Integer.parseInt("TTT");
	}
}

class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.printf("An exception has been captured\n");
		System.out.printf("Thread: %s\n",t.getId());
		System.out.printf("Exception: %s: %s\n", e.getClass().getName(),e.getMessage());
		System.out.printf("Stack Trace: \n");
		e.printStackTrace(System.out);
		System.out.printf("Thread status: %s\n",t.getState());
	}
}