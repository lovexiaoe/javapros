package com.zhaoyu.generic;

public class GenericDefine {

}

/**
 * ���ͱ���ʹ�ô�д��ʽ����java��ʹ��E��ʾ���ϵ�Ԫ�����ͣ�K��V�ֱ��ʾ��Ĺؼ�����ֵ�����͡�
 * T(��Ҫʱ���������ٽ�����ĸU��S)��ʾ�������͡�
 *
 * �þ���������滻���ͱ����Ϳ���ʵ�����������ͣ���
 * Pair<String>
 *
 * �������͵��޶�����
 * T extends Comparable & Serializable
 * &���ڷָ�����޶���
 *
 * @author xiaoE
 *
 * @param <T>
 */
class Pair<T> {
	private T first;
	private T second;

	// getters and setters
	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}

	// constructors
	public Pair() {
		first = null;
		second = null;
	};

	public Pair(T first, T second) {
		this.first = first;
		this.second = second;
	};
}
