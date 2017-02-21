package com.zhaoyu.designpattern.singleton;

public final class Singleton {
	// ����ʹ�õ����ӳټ��أ�����getSingleTon��������ʱ����������
	private static Singleton singleton;
	String s;

	private Singleton(String s) {
		this.s = s;
	}

	public String getS() {
		return s;
	}

	public void setS(String ss) {
		s = ss;
	}

	public static Singleton getSingleTon() {
		// ˫��������ʽ����֤�̰߳�ȫ��ʼ��
		// ���ģʽ��ͬ�������·���if�ڲ��������ִ�е�Ч�ʣ�����ÿ�λ�ȡ����ʱ������ͬ����ֻ�е�һ�β�ͬ�����������Ժ��û��Ҫ�ˡ�
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton("��ֻ��һ��ʵ��");
				}
			}
		}
		return singleton;
	}

	public static void main(String[] args) {
		Singleton singleton1 = Singleton.getSingleTon();
		System.out.println(singleton1.getS());
	}
}
