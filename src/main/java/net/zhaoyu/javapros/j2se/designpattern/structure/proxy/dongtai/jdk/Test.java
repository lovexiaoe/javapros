package net.zhaoyu.javapros.j2se.designpattern.structure.proxy.dongtai.jdk;

/**
 * java动态代理中，InvocationHandler接口和Proxy类是核心。java代理中需要被代理类实现接口。
 */
public class Test {
	public static void main(String[] args) {
		LogHandler logHandler = new LogHandler();
		UserManager userManager = (UserManager) logHandler.newProxyInstance(new UserManagerImple());

		String name = userManager.findUser("0003");
		System.out.println("=========" + name);
	}
}
