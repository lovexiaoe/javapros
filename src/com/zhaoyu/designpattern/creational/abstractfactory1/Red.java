package com.zhaoyu.designpattern.creational.abstractfactory1;

public class Red implements Color {
	@Override
	public void fill() {
		System.out.println("Inside Green::fill() method.");
	}
}
