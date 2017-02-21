package com.zhaoyu.designpattern.structure.proxy.dongtai;

public class Test {
	public static void main(String[] args) {
		LogHandler logHandler = new LogHandler();
		UserManager userManager = (UserManager) logHandler.newProxyInstance(new UserManagerImple());

		String name = userManager.findUser("0003");
		System.out.println("=========" + name);
	}
}
