package com.zhaoyu.designpattern.structure.proxy.dongtai;

public class UserManagerImple implements UserManager {

	public void addUser(String id, String name) {
		System.out.println("这是被代理的添加程序！");
	}

	public void delUser(String id) {
		System.out.println("这是被代理的删除程序");
	}

	public String findUser(String id) {
		System.out.println("这是被代理的有返回值的程序");
		return "张三";
	}
}
