package com.zhaoyu.generic;

/**
 * �����û�з������Ͷ������еĶ���������ͨ�ࡣ
 * �������ʹ����java����,�Ͳ�����5.0֮ǰ������������С�
 *
 * ���Ͳ�����erased��----����jvmû�ж�Ӧ�ķ������ͣ�
 * ��Ե�һ�ַ������ͣ�jvm�����ṩһ����Ӧ��ԭʼ����(raw type)��
 * �����ڱ���ʱ��jvm�ͻ�ȥ�����ͱ������滻Ϊ��Ӧ���޶����ͣ���<? extends Comparable>���޶�����ΪComParable����
 * ���û���޶�����ʹ��Object��
 *
 * ��������÷��ͷ���ʱ����������������ͣ�����������ǿ������ת�������磬�������������� <code>
 * 	Pair<Employee> buddies=..;
 * 	Employee buddy=buddies.getFirst();
 * </code>
 *
 * ���Ͳ���Ҳ������ڷ��ͷ����С������淽�� <code>
 * 	public static <T extends Comparable> T min(T[] a)
 * </code>
 *
 * �����Ͳ�����ֻʣ��һ�������� <code>
 * 	public static  Comparable min(Comparable[] a)
 * </code>
 *
 * ���������������ӵ����⡣ <code>
 * Class DateInterval extends Pair<Date>
 * {
 * 	public void setSecond(Date second){
 * 		super.setSecond(second);
 * 	}
 * }
 * </code>
 *
 * ���Pair�з���setSecond(Object second),��ô�����Ͳ��������������ͬ��setSecond��������ʱ���Ͳ����Ͷ�̬����
 * ������ͻ��Ҫ���������⣬����Ҫ��������DataInteral������һ���ŷ�����bridge method��:
 * public void setSecond(Object second){
 * setSecond((Date)second);
 * }
 *
 * �ŷ�����ú���֡����DateInterval������getSecond��������ô�����Ͳ����󣬻�������getSecond���� <code>
 * 	Date getSecond()  //��DateInterval�ж����
 * 	Object getSecond() //��Pair�ж���ġ�
 * </code>
 *
 * �ŷ���ֻ��������ڴ������Ͳ���ʱ�Ļ��ƣ�����������ʽ�Ĵ��롣
 *
 * ��֮��java����ת����4��Ҫ�أ�
 * 1�������û�з��ͣ�ֻ����ͨ����ͷ�����
 * 2�����е����Ͳ����������ǵ��޶������滻
 * 3���ŷ������ϳ������ֶ�̬��
 * 4��Ϊ�������Ͱ�ȫ�ԣ���Ҫʱ����ǿ������ת����
 *
 * @author xiaoE
 *
 */
public class GenericAndJVM {

}

/**
 * �����pairԭʼ���͵Ķ��塣�����Ͳ�����Ķ��� ��
 *
 * �ڳ����п��԰�����ͬ���͵�Pair�����磬Pair<String> ��Pair<Calendar>
 * ���ڲ������ͺ�ͱ��ԭʼ��Pair�����ˡ�
 *
 * @see com.zhaoyu.generic.Pair
 * @author xiaoE
 *
 * @param <T>
 */
class Pair1 {
	private Object first;
	private Object second;

	// getters and setters
	public Object getFirst() {
		return first;
	}

	public void setFirst(Object first) {
		this.first = first;
	}

	public Object getSecond() {
		return second;
	}

	public void setSecond(Object second) {
		this.second = second;
	}

	// constructors
	public Pair1() {
		first = null;
		second = null;
	};

	public Pair1(Object first, Object second) {
		this.first = first;
		second = first;
	};
}
