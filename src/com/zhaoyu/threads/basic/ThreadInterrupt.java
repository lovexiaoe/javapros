package com.zhaoyu.threads.basic;

/**
 * ���̵߳�run�������������δ������쳣������run����ִ����ϣ��߳̽�����ֹ��
 * ��java�����ڰ汾�У��ṩ��stop������suspend������
 * ֱ����ֹ�̣߳��ͷ��߳��������Դ���������ͷŹ����л���ɶ���״̬��һ�£��Ӷ�ʹ�������δ֪�ľ��أ��ѱ����á�
 *
 * ��һ���̵߳���interrupt����ʱ��������������ж��̣߳�ֻ�ǽ��̵߳��ж�״̬��λ��
 * �ж�״̬Ϊtrue������ÿһ���̶߳����е�boolean��־��
 *
 * ����ҪŪ����ж�״̬�Ƿ���λ������̱߳����������޷�����ж�״̬��
 *
 * ��һ�����������̣߳�����sleep��wait������interrupt����ʱ���������ᱻInterruptedException�쳣�жϡ�
 * ��һ��δ�������̵߳���interrupt����ʱ��ֻ�Ǳ�����ж�״̬, �̵߳�ִ�в������ܵ�Ӱ�죬�������ж�״̬���߳̿��Ծ��������Ӧ�жϡ�
 * ĳЩ�߳��������Ҫ�������ڴ�����InterruptedException�����ִ�С�
 *
 * void interrupt���������Ŀǰ�̱߳�һ��sleep�����������׳�InterruptedException�쳣��
 * ���򣬽��ж�״̬����Ϊtrue��
 *
 * isInterruptedֻ�Ƿ����ж�״̬������ı�״̬ ��
 *
 * static boolean interrupted
 * ���Ե�ǰ�߳��Ƿ��жϣ���һ���û���������ã�������ǰ�̵߳��ж�״̬����Ϊfalse��
 *
 * @author xiaoE
 *
 */
public class ThreadInterrupt {
	public static void main(String[] args) {
		// �߳��ж�״̬����
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
		System.out.println("���̵߳Ĵ��״̬��" + Thread.currentThread().isInterrupted());
		System.out.println(Thread.interrupted());
		// ������״̬�¼����״̬�᷵��false��
		System.out.println("t1���д��ǰ��״̬��" + t1.isInterrupted());
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
		System.out.println("t1���д�Ϻ��״̬��" + t1.isInterrupted());
	}

	public static void interruptTest() {
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println("���߳�����Ҫ��ʱ�ؼ��interrupt��־�������߳��Ƿ��жϡ�");
			return;
		}
	}

	/**
	 * ���ÿ��ѭ��������sleep���������������жϷ�����wait��,isInterrupted���û�б�Ҫ��
	 * ��Ϊ���ж�״̬�£�����sleep���̲߳������ߡ�������������ж�״̬���׳�һ��InterruptException��
	 *
	 * �������߳��е�����sleep����wait����������Ҫ����isInterrupt��飬�෴����Ҫ����InterruptException��
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
 * �̲߳�����
 *
 * @author xiaoE
 *
 */
class ThreadRunTest implements Runnable {

	private int i = 0;

	@Override
	public void run() {
		System.out.println("�����߳̿�ʼ");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		System.out.println("�����߳�sleep�ˣ�" + 5 + "��");
	}

	public int getI() {
		return i;
	}
}
