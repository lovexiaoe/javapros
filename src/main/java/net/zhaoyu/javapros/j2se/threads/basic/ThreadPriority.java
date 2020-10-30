package net.zhaoyu.javapros.j2se.threads.basic;

/**
 * 优先级从1（MIN_PRIORITY）——10(MAX_PRIORITY)，默认是5。
 * 使用setPriority方法提高或降低任何一个线程的优先级。系统的优先级有可能和java的优先级不一样，当java的优先级
 * 映射到系统的优先级时，有可能会多，有可能会少。
 *
 * yield让线程给其他同等优先级的线程让出资源。
 *
 * @author xiaoE
 *
 */
public class ThreadPriority {
	public static void main(String[] args) {
		ThreadRunTest r1 = new ThreadRunTest();
		Thread t1 = new Thread(r1);
		/**
		 * 守护线程的唯一用途是给其它线程提供服务。如定时地给其它”嘀嗒“信号给其它线程等。
		 * 当只剩下守护进程时，虚拟机就会退出 。
		 *
		 * 下面将t1设置为守护线程，守护线程永远不去访问固有资源，如文件，数据库等。
		 * 这一方法必须在线程启动之前调用 。
		 */
		// t1.setDaemon(true);
		t1.start();

		ThreadRunTest r2 = new ThreadRunTest();
		Thread t2 = new Thread(r2);
		t2.start();
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MAX_PRIORITY);

		// yield让当前执行的main程序让步，如果有其它可运行线程具有与此线程相同的优先级，那么这些
		// 线程接下来将会被调用。
		Thread.yield();

	}

}
