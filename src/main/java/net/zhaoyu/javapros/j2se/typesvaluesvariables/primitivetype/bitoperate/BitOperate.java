package net.zhaoyu.javapros.j2se.typesvaluesvariables.primitivetype.bitoperate;

/**
 * 编程语言中的整型数据类型都是用补码的形式存放的，正数的补码是它本身，负数的补码符号位不变，其他位取反+1。
 * 负数的补码还原时也是取反+1。
 * 左移时，符号位不变，低位补0
 * 右移时，补码的形式高位补符号位。
 * 无符号位右移时，高位都补0，负数在无符号右移中会变成正数。结果会变得无意义。
 *
 * 当操作数是int时，位移运算符右侧的参数要进行模32的运算。如1<<35或1<<3或8是相同的。
 * 当操作数是long时，要进行模64操作。
 *
 * @author xiaoe
 */
public class BitOperate {
	public static void main(String[] args) {
		System.out.println("将2左移3位得：" + (2 << 3));            //2*8=16
		System.out.println("将-2左移3位得：" + (-2 << 3));          //-2*8=-16
		System.out.println("将3右移1位得：" + (3 >> 1));            //正数右移，高位补0得。
		System.out.println("将-6右移1位得：" + (-6 >> 1));			//负数-6的补码类似1111 1010 右移1位高位补1得1111 1101 取反+1得原码1000 0011=-3
		System.out.println("将-2右移3位得：" + (-2 >> 35));          //-2>>35==-2>>3==-1
		System.out.println("将4无符号右移3位得：" + (4 >>> 3));     //0000 0100右移得0。
		System.out.println("将-4无符号右移1位得：" + (-4 >>> 1));   //-4的补码后8位为1111 1100 无符号后移1位，得2的31次方减1再减1=2147483646
		System.out.println("取13的二进制位的倒数第三位：" + shadowBit8(13));
	}

	/**
	 * 根据位与运算取得二进制位的倒数第三位。
	 *
	 * @param n
	 * @return
	 */
	static int shadowBit8(int n) {
		return (n & 8) / 8;
		// 也可以写成这样 return (n&(1<<3))>>3;
	}
}
