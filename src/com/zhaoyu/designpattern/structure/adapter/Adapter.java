package com.zhaoyu.designpattern.structure.adapter;

//����������
public class Adapter implements Target {

	// ���뱻������
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
		System.out.println("����������");
	}
}
