package com.zhaoyu.designpattern.structure.adapter;

//��������
public class Adapter1 extends Adaptee implements Target {

	@Override
	public void adapterMethod() {
		System.out.println("����������");
	}

}
