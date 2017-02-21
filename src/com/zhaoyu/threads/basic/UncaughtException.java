package com.zhaoyu.threads.basic;

/**
 * �̵߳�run���������׳��κα������쳣(����run�������׳�InterruptException�ᱨ��)��δ�����쳣�ᵼ���߳���ֹ��
 *
 * ����Ҫ���κ�catch�Ӿ���������ɴ������쳣�����߳�����֮ǰ ���쳣�����ݵ�һ������δ�����쳣�Ĵ������С�
 * ������ʵ��UncaughtExceptionHandler�ӿڡ�����ӿ�ֻ��һ��������
 * void uncaughtException(Thread t,Throwable e);
 *
 * ����ͨ�� setUncaughtExceptionHandler����Ϊ�κ��߳�����һ����������Ҳ����ʹ��
 * Thread�ľ�̬����setDefaultUncaughtExceptionHandlerΪ�����̰߳�װһ��Ĭ�ϴ�������
 *
 * ThreadGroup��ʵ����UncaughtExceptionHandler�ӿڣ����û�и��߳�ָ��Ĭ�ϵĴ���������ô��ʱ�Ĵ�����
 * ���Ǹ��̵߳�ThreadGroup����
 *
 * @author xiaoE
 *
 */
public class UncaughtException {

}

/**
 * �̲߳�����
 *
 * @author xiaoE
 *
 */
class ThreadRunTest1 implements Runnable {

	private int i = 0;

	@Override
	public void run() throws InterruptedException {
		System.out.println("�����߳̿�ʼ");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new InterruptedException();
		}
		System.out.println("�����߳�sleep�ˣ�" + 5 + "��");
	}

	public int getI() {
		return i;
	}
}
