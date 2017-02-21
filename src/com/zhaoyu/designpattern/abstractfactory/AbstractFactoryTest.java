package com.zhaoyu.designpattern.abstractfactory;

/**
 * 抽象工厂是工厂模式的扩展版。使工厂模式拥有生产多个产品的能力。将工厂模式改进可得到抽象工厂模式。
 * 个人总结：抽象工厂则像麦当劳和肯德基，鸡腿，汉堡、薯条等都在生成。你去麦当劳能吃到鸡腿，去肯德基也是。
 * 
 * @author zhaoyu
 * 
 */
public class AbstractFactoryTest {
	public static void main(String[] args) {
		// 在KFC里面喝cola
		AbstractFactory abstractFactory = new KFC();

		Cola cola = abstractFactory.genCola();
		cola.drink();
	}
}
