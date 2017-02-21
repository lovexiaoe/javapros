package com.zhaoyu.designpattern.factory;

/**
 * 工厂模式 ：针对工厂只生产某一种产品的情形， 如鞋厂生产鞋子，nake鞋厂生产nake鞋，Addidas鞋生产Addidas鞋。
 * 工厂模式遵从开闭原则（对扩展开放；对修改封闭） 工厂方法创建一般只有一个方法，创建一种产品。
 * 如果使工厂具有生产多种产品的能力，则改进后，你会有趣地发现，它会变成抽象工厂模式。
 * 
 * @author zhaoyu
 * 
 */
public class FactoryTest {

	public static void main(String[] args) {
		// 穿nake鞋
		Factory nakeFactory = new NakeFactory();
		Shoe shoe = nakeFactory.generateShoe();
		System.out.println(shoe.getShoeName());

		// 穿addidas鞋
		Factory addidasFactory = new AddidasFactory();
		Shoe shoe1 = addidasFactory.generateShoe();
		System.out.println(shoe1.getShoeName());
	}

}
