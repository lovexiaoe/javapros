package com.zhaoyu.designpattern.j2ee.frontcontroller;

public class FrontControllerDemo {
	public static void main(String[] args) {
		FrontController frontController = new FrontController();
		frontController.dispatchRequest("HOME");
		frontController.dispatchRequest("STUDENT");
	}
}
