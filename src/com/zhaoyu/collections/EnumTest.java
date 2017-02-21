package com.zhaoyu.collections;

public class EnumTest {
	public static void main(String[] args) {

		String str1 = "LARGE";
		// valueOf是toString的逆方法。
		Size size = Enum.valueOf(Size.class, str1);
		System.out.println("size=" + size);
		System.out.println("abbreviation=" + size.getAbbreviation());

		// 第个枚举都有一个静态方法values，返回一个包含全部枚举的数组。
		Size[] values = Size.values();
		System.out.println(values[1]);

		// ordinal方法返回常量在enum声明中的位置，从0开始计数。
		System.out.println(Size.SMALL.ordinal());
	}
}

enum Size {
	SMALL("S"), MEDIUM("M"), LARGE("L");
	// 加;后可以定义自己的枚举，构造函数需要设置为私有。这里S,M,L就是我们自定义的属性abbreviation.
	private Size(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	// abbreviation缩写，简写。
	private String abbreviation;
}
