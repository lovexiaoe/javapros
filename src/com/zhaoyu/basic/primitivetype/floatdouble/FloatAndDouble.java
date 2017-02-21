package com.zhaoyu.basic.primitivetype.floatdouble;

/**
 * 此类用于说明float和double类型的相关知识和使用。
 * flat类型的数值后有一个F(如：3.402F),没有后缀F的浮点数值默认为double类型。double类型也可以 在后面添加D(如3.402D)。
 *
 * float
 * 在java中使用4字节表示，取值范围：+-3.40282347E+38F E+38表示乘以10的38次方。e-28表示乘以10的-28次方。
 * 但是float类型表示的精度有限，只有8-9位的精度，所以一般都不用float类型，使用double。
 *
 * double 在java中使用8字节来表示 ，表示精度是float精度的两倍。
 *
 * 在jdk5中，可以 使用十六进制来表示浮点数值。如0.125可以表示成0x1.0p-3。在十六进制中用p表示指数。而不是e
 * 尾数采用16进制，指数采用10进制，指数的基数是2，而不是10。
 *
 * 可用使用Double.isNaN(x)，来判断x是否是一个非数据型的值 。
 *
 * @author xiaoe
 *
 */
public class FloatAndDouble {
	public static void main(String[] args) {
		Float f1 = 1.2812312323E20F;
		double f2 = 1281231232.3e29;
		System.out.println(f1.toString());
		System.out.println(String.valueOf(f2));
		System.out.println(String.valueOf("max:" + f1.MAX_VALUE));
		System.out.println(String.valueOf("min:" + f1.MIN_VALUE));
	}
}

/*
 * 输出结果。
 * 1.2812313E20
 * 1.2812312323E38
 * max:3.4028235E38
 * min:1.4E-45
 */
