package com.zhaoyu.designpattern.structure.adapter;

//对象适配器
public class Adapter implements Target {

	// 引入被适配者
	private Adaptee adaptee;

	public Adapter(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void adapteeMethod() {
		adaptee.adapteeMethod();
	}

	@Override
	public void adapterMethod() {
		System.out.println("我是适配者");
	}
}
