package com.zhaoyu.designpattern.structure.proxy.jintai;

public class ProxyObject implements Object {

	Object obj;

	public ProxyObject() {
		System.out.println("���Ǵ�����");
		obj = new ObjectImpl();
	}

	@Override
	public void action() {
		System.out.println("����ʼ");
		obj.action();
		System.out.println("�������");
	}

}
