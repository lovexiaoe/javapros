package com.zhaoyu.io.standerdio;

import java.util.Date;

public class Sysout {
	/**
	 * 标准输出类型包括 ：f浮点数，s字符串，d十进制整数。
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 3.3333333333333335 int 最大长度输出
		System.out.println(10.0 / 3.0);

		// 格式化符点数,以8位字符长度（包括了小数点，小数），2位小数精度打印
		// 长度不足8位加空格
		/*
		 * 输出结果
		 * 333333333.32
		 * 33.32
		 */
		System.out.printf("%8.2f", 333333333.323);
		System.out.println();
		System.out.printf("%8.2f", 33.323);
		System.out.println();

		// 逗号标志增加了分组的分隔符。即：3,333.33
		System.out.printf("%,.2f\n", 10000.0 / 3.0);

		// 输出时间 :星期六 三月 28 16:45:49 CST 2015
		System.out.printf("%tc\n", new Date());
		// 2015-03-28
		System.out.printf("%tF\n", new Date());
		// 如果需要进行更细化的日期参数控制，我们可以指出被格式化的参数的索引。索引必须跟在%后面，并以$终止。
		// 这里参数的序列是从1开始。
		System.out.printf("%1$s %2$tB %2$te %2$ty", "Due date:", new Date());

	}
}
