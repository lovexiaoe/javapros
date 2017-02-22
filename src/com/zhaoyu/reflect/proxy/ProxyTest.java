package com.zhaoyu.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author xiaoE
 *
 */
public class ProxyTest {
	public static void main(String[] args) {
		// Integer实现了Comparable接口，将Integer对象传入代理。构造一个Integer代理的数组。
		Object[] elements = new Object[1000];
		for (int i = 0; i < elements.length; i++) {
			Integer value = i + 1;
			InvocationHandler handler = new TraceHandler(value);
			Object proxy = Proxy.newProxyInstance(null, new Class[] { Comparable.class }, handler);
			elements[i] = proxy;
		}
		// 构造一个Integer，这个Integer对象是代理数组中的某个值 。
		Integer key = new Random().nextInt(elements.length) + 1;
		// 用二分法查找上面的值 ，二分法查找时要用到compareTO方法。数组中都是代理对象，会调用代理中的方法。
		// 代理对象属性在运行时定义的类，它有一个名字，如$Proxy0
		int result = Arrays.binarySearch(elements, key);

		// 打印结果中会有一个toString显示,创建InvokeHandler的时候不仅传入了comparable接口，也传入了Object的toString方法。
		// 所以打印元素时会有一个toString方法被代理。
		if (result >= 0) {
			System.out.print(result + " ---");
			System.out.println(elements[result]);
		}
	}
}

/**
 * 该类定义一个一TraceHander,其中invoke方法打印出被调用方法的名字和参数 。然后调用这个方法。
 *
 * @author xiaoE
 *
 */
class TraceHandler implements InvocationHandler {

	/**
	 * 被调用方法的所属对象。用于传给调用处理器。
	 */
	private Object target;

	public TraceHandler(Object object) {
		target = object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 打印被调用方法所属对象
		System.out.print(target);
		// 打印被调用方法名。
		System.out.print(method.getName());
		// 打印参数
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i]);
				if (i < args.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println(")");

		return method.invoke(target, args);
	}

}
