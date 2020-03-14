package net.zhaoyu.javapros.j2se.oop.classdetail;

/**
 * is-a的另一个表述法为“置换法则”，它表明程序中出现父类对象的任何地方 ，都可以用子类对象置换。
 * 
 * @author xiaoe
 *
 */
public class Polymorphism {
	public static void main(String[] args) {
		Son son = new Son();
		Polymorphism[] fathers = new Polymorphism[3];
		fathers[0] = son;
		// 调用失败，fathers[0]的类型是father,不能调用son的方法。应该由son来调用。
		// fathers[0].sonFoo();
		son.sonFoo();

		// 多态的体现，一个PolyMorphism的对象在被赋于不同的子类变量时，会表现出不同的行为。
		Polymorphism father = new Son();
		father.foo();
	}

	int i = 1;

	void foo() {
		System.out.println("do someThings!");
	}
}

class Son extends Polymorphism {
	@Override
	void foo() {
		System.out.println("son do someThing!");
	}

	void sonFoo() {
		System.out.println("son's method!");
	}
}
