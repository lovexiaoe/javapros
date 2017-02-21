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
		// Integerʵ����Comparable�ӿڣ���Integer�������������һ��Integer��������顣
		Object[] elements = new Object[1000];
		for (int i = 0; i < elements.length; i++) {
			Integer value = i + 1;
			InvocationHandler handler = new TraceHandler(value);
			Object proxy = Proxy.newProxyInstance(null, new Class[] { Comparable.class }, handler);
			elements[i] = proxy;
		}
		// ����һ��Integer�����Integer�����Ǵ��������е�ĳ��ֵ ��
		Integer key = new Random().nextInt(elements.length) + 1;
		// �ö��ַ����������ֵ �����ַ�����ʱҪ�õ�compareTO�����������ж��Ǵ�����󣬻���ô����еķ�����
		// �����������������ʱ������࣬����һ�����֣���$Proxy0
		int result = Arrays.binarySearch(elements, key);

		// ��ӡ����л���һ��toString��ʾ,����InvokeHandler��ʱ�򲻽�������comparable�ӿڣ�Ҳ������Object��toString������
		// ���Դ�ӡԪ��ʱ����һ��toString����������
		if (result >= 0) {
			System.out.print(result + " ---");
			System.out.println(elements[result]);
		}
	}
}

/**
 * ���ඨ��һ��һTraceHander,����invoke������ӡ�������÷��������ֺͲ��� ��Ȼ��������������
 *
 * @author xiaoE
 *
 */
class TraceHandler implements InvocationHandler {

	/**
	 * �����÷����������������ڴ������ô�������
	 */
	private Object target;

	public TraceHandler(Object object) {
		target = object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// ��ӡ�����÷�����������
		System.out.print(target);
		// ��ӡ�����÷�������
		System.out.print(method.getName());
		// ��ӡ����
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
