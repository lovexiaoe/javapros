package com.zhaoyu.designpattern.structure.proxy.dongtai;

public class UserManagerImple implements UserManager {

	public void addUser(String id, String name) {
		System.out.println("���Ǳ��������ӳ���");
	}

	public void delUser(String id) {
		System.out.println("���Ǳ������ɾ������");
	}

	public String findUser(String id) {
		System.out.println("���Ǳ�������з���ֵ�ĳ���");
		return "����";
	}
}
