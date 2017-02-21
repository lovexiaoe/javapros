package com.zhaoyu.generic;

public class GenericDefine {

}

/**
 * 类型变量使用大写形式，在java中使用E表示集合的元素类型，K和V分别表示表的关键字与值的类型。
 * T(需要时还可以用临近的字母U和S)表示任意类型。
 *
 * 用具体的类型替换类型变量就可以实例化泛型类型，如
 * Pair<String>
 *
 * 泛型类型的限定：如
 * T extends Comparable & Serializable
 * &用于分隔多个限定。
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
