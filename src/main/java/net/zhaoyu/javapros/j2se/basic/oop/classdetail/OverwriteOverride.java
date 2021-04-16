package net.zhaoyu.javapros.j2se.basic.oop.classdetail;

/**
 * 重载判定需要方法名相同，参数不同（类型或者个数），返回值和修饰符无限定。
 * 重写方法不能对private/static/final方法进行，且遵从”两同两小一大“原则：
 * 1.两同：方法名，参数列表相同。
 * 2.两小：返回值类型，子类方法应比父类小或者相等。跑出的异常，子类抛出的异常应该比父类小或者相等。
 * 3.一大：子类方法的权限可以比父类的访问权限大或者相等。
 */
public class OverwriteOverride {
	public static void main(String[] args) {

	}
}

class Father {
	int i;

	int add(int a, int b) {
		return a + b;
	}

	protected Object getInfo(int a,int b) throws RuntimeException{
		return "info";
	}
}

class Son2 extends Father {
	// 名称相同，参数不同，是对add方法的重载。
	int add(int a) {
		return a + 1;
	}

	// 重写时，返回类型必须相同，下面方法会报错。
	/*@Override
	double add(int a, int b) {
		return a + b;
	}*/

	//重写
	@Override
	public String getInfo(int a,int b) throws IllegalArgumentException{
		return "info";
	}

	double add(int a, int b, int c) {
		return a + b + c;
	}

	// 重载时，不考虑返回类型，只考虑方法名和参数，
	// 方法名相同时，参数也相同 ，说明方法是一个方法，会报错。
	/*int add(int a, int b, int c) {
		return a + b + c;
	}*/
}
