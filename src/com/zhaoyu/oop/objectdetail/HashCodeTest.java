package com.zhaoyu.oop.objectdetail;

/**
 * hashCode也是Object的方法。
 * 如果要重写equals方法，就必须重写hashCode方法，以便用户可以将对象插入到散列表中。
 *
 * equals与hashCode方法必须保持一致，x.equals(y)返回true，那么x.hashCode和y.hashCode必须返回相同的值。
 * 例如：如果定义Employee.equals比较的是雇员的ID，那么定义hashCode时就要散列ID，而不是雇员的名称或者其它信息。
 *
 * @author xiaoe
 *
 */
public class HashCodeTest {
	public static void main(String[] args) {
		System.out.println("hashCode是Object的方法，");
	}
}
