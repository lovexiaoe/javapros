package com.zhaoyu.generic.why;

import java.util.ArrayList;

/**
 * 如下，在java5之前，还没有加入泛型，ArrayList等聚集类只会维护Object类型的元素，
 * 对于操作，都需要进行强制转换。
 * 此外，在添加的时候可以添加任意类型的对象。对于这个操作，编译和运行都不会出错。
 * 但是在其它地方取出进行类型转换时，就有可能发生异常。
 *
 * 泛型提供了参数类型（type parameters）很好地解决了这一问题。
 *
 * @author xiaoE
 *
 */
public class WhyTest {
	public static void main(String[] args) {
		ArrayList li = new ArrayList();
		li.add("我是第一个元素");

		// 需要强制转换
		String str = (String) li.get(0);

		// 可以添加任意的元素
		int i = 2;
		li.add(i);
	}
}
