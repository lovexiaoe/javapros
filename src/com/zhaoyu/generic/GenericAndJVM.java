package com.zhaoyu.generic;

/**
 * 虚拟机没有泛型类型对象，所有的对象都属于普通类。
 * 如果代码使用了java泛型,就不能在5.0之前的虚拟机上运行。
 *
 * 类型擦除（erased）----由于jvm没有对应的泛型类型，
 * 针对第一种泛型类型，jvm都会提供一个相应的原始类型(raw type)。
 * 所以在编译时，jvm就会去掉类型变量，替换为对应的限定类型（如<? extends Comparable>的限定类型为ComParable），
 * 如果没有限定，则使用Object。
 *
 * 当程序调用泛型方法时，如果擦除返回类型，编译器插入强制类型转换。例如，下面这个语句序列 <code>
 * 	Pair<Employee> buddies=..;
 * 	Employee buddy=buddies.getFirst();
 * </code>
 *
 * 类型擦除也会出现在泛型方法中。如下面方法 <code>
 * 	public static <T extends Comparable> T min(T[] a)
 * </code>
 *
 * 在类型擦除后，只剩下一个方法： <code>
 * 	public static  Comparable min(Comparable[] a)
 * </code>
 *
 * 这样带来两个复杂的问题。 <code>
 * Class DateInterval extends Pair<Date>
 * {
 * 	public void setSecond(Date second){
 * 		super.setSecond(second);
 * 	}
 * }
 * </code>
 *
 * 如果Pair有方法setSecond(Object second),那么在类型擦除后会有两个相同的setSecond方法，这时类型擦除和多态方法
 * 发生冲突。要解决这个问题，就需要编译器在DataInteral中生成一个桥方法（bridge method）:
 * public void setSecond(Object second){
 * setSecond((Date)second);
 * }
 *
 * 桥方法变得很奇怪。如果DateInterval覆盖了getSecond方法，那么在类型擦除后，会有两个getSecond方法 <code>
 * 	Date getSecond()  //在DateInterval中定义的
 * 	Object getSecond() //在Pair中定义的。
 * </code>
 *
 * 桥方法只是虚拟机在处理类型擦除时的机制，不能用于正式的代码。
 *
 * 总之，java泛型转换有4个要素：
 * 1，虚拟机没有泛型，只有普通的类和方法。
 * 2，所有的类型参数都用它们的限定类型替换
 * 3，桥方法被合成来保持多态。
 * 4，为保持类型安全性，必要时插入强制类型转换。
 *
 * @author xiaoE
 *
 */
public class GenericAndJVM {

}

/**
 * 这个是pair原始类型的定义。即类型擦除后的定义 。
 *
 * 在程序中可以包含不同类型的Pair，例如，Pair<String> 或Pair<Calendar>
 * 而在擦除类型后就变成原始的Pair类型了。
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
