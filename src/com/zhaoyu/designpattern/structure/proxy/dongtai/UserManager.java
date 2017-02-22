package com.zhaoyu.designpattern.structure.proxy.dongtai;

/**
 * author:大神
 */
public interface UserManager {
	public void addUser(String id, String name);

	public void delUser(String id);

	public String findUser(String id);

}
