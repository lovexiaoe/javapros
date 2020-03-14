package net.zhaoyu.javapros.j2se.threads.lockcondition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于理解trylock及超时。
 *
 * @author xiaoE
 *
 */
public class TrylockTest {
	public static void main(String[] args) {
		TrylockClazz clazz1 = new TrylockClazz();
		ThreadRunTest2 r2 = new ThreadRunTest2(clazz1);
		Thread t2 = new Thread(r2);
		t2.start();

		ThreadRunTest2 r3 = new ThreadRunTest2(clazz1);
		Thread t3 = new Thread(r3);
		t3.start();
	}
}

class ThreadRunTest2 implements Runnable {
		TrylockClazz clazz;

	public ThreadRunTest2(TrylockClazz clazz) {
		this.clazz = clazz;
	}

	@Override
	public void run() {
		System.out.println("线程：" + Thread.currentThread().getId() + " 的i加1再*2开始，i为：" + clazz.getI());
		clazz.addI();
		System.out.println("线程：" + Thread.currentThread().getId() + "测试end! i为：" + clazz.getI());
	}

}

class TrylockClazz {
	private int i = 0;
	Lock iLock = new ReentrantLock();

	public int addI() {
		try {
			// 尝试锁与超时。
			if (iLock.tryLock(100, TimeUnit.MILLISECONDS)) {
				i++;
				i = i * 2;
				//如果tryLock失败，unlock会报错，所以这里最好不要放在final中，并且之间的代码保证不出错。
				iLock.unlock();
			}

		} catch (InterruptedException e) {
			//打断处理
		}
		return i;
	}

	public int getI() {
		return i;
	}
}