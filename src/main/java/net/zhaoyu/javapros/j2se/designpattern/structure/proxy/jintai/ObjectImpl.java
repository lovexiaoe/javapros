package net.zhaoyu.javapros.j2se.designpattern.structure.proxy.jintai;

public class ObjectImpl implements Object {
	@Override
	public void action() {
		System.out.println("========");
		System.out.println("这是被代理的类");
		System.out.println("========");
	}

}
