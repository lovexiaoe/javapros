package com.zhaoyu.designpattern.structure.adapter;

//类适配器
public class Adapter1 extends Adaptee implements Target {

	@Override
	public void adapterMethod() {
		System.out.println("我是适配者");
	}

}
