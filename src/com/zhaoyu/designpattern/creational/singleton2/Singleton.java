package com.zhaoyu.designpattern.creational.singleton2;

public final class Singleton {
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
}
