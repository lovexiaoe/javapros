package com.zhaoyu.threads.basic;

/**
 * ���ȼ���1��MIN_PRIORITY������10(MAX_PRIORITY)��Ĭ����5��
 * ʹ��setPriority������߻򽵵��κ�һ���̵߳����ȼ���ϵͳ�����ȼ��п��ܺ�java�����ȼ���һ������java�����ȼ�
 * ӳ�䵽ϵͳ�����ȼ�ʱ���п��ܻ�࣬�п��ܻ��١�
 *
 * @author xiaoE
 *
 */
public class ThreadProperty {
	public static void main(String[] args) {
		ThreadRunTest r1 = new ThreadRunTest();
		Thread t1 = new Thread(r1);
		/**
		 * �ػ��̵߳�Ψһ��;�Ǹ������߳��ṩ�����綨ʱ�ظ���������ડ��źŸ������̵߳ȡ�
		 * ��ֻʣ���ػ�����ʱ��������ͻ��ܳ� ��
		 *
		 * ���潫t1����Ϊ�ػ��̣߳��ػ��߳���Զ��ȥ���ʹ�����Դ�����ļ������ݿ�ȡ�
		 * ��һ�����������߳�����֮ǰ���� ��
		 */
		// t1.setDaemon(true);
		t1.start();

		ThreadRunTest r2 = new ThreadRunTest();
		Thread t2 = new Thread(r2);
		t2.start();
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MAX_PRIORITY);

		// yield�õ�ǰִ�е�main�����ò�������������������߳̾�������߳���ͬ�����ȼ�����ô��Щ
		// �߳̽��������ᱻ���á�
		Thread.yield();

	}

}
