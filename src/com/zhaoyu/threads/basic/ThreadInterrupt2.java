package com.zhaoyu.threads.basic;

public class ThreadInterrupt2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Thread thread1 = new Thread() {
			@Override
			public void run() {
				try {
					long time = System.currentTimeMillis();
					while (System.currentTimeMillis() - time < 2000) {
					}
					System.out.println("A1");
				} catch (Exception e) {
					System.out.println("B1");
				}
			}
		};
		thread1.start();
		thread1.interrupt();

		// ���߳�sleep״̬�½����ж�
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

		// ���߳�wait״̬�½����ж�,����wait()û����ͬ������
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

		// ���߳�wait״̬�½����ж�,����wait()��ͬ�����У�
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

		try {
			// ����ᱨIllegalThreadStateException�쳣������Ӧ�����ٴε���start��ԭ��
			thread4.start();
			System.out.println("A5");
		} catch (Exception e) {
			System.out.println("B5");
			System.out.println(e.toString());
		}

	}
}
