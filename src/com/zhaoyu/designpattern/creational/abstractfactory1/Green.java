package com.zhaoyu.designpattern.creational.abstractfactory1;

public class Green implements Color {
	@Override
	public void fill() {
		System.out.println("Inside Red::fill() method.");
	}
}
