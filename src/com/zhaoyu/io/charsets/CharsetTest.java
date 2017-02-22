package com.zhaoyu.io.charsets;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * javase 1.4中引入的java.nio包引用Charset类统一对字符集进行转换。
 * 可以通过forName方法来获得一个Charset，只需向这个方法传递一个官方名字或者它的某个别名：
 * Charset cset=Charset.forName(“ISO-8859-1”);
 * 字符集名字是大小写不敏感的。每个字符集都可以有很多别名。例如：ISO-8859-1的别名有：
 * ISO8859-1
 * ISO_8859_1
 * 8859-1
 * latin1
 * ……
 * aliases方法可以返回由别名构成的Set对象：
 * Set<String> aliases=cset.aliases();
 *
 * 为了确定某个特定环境中哪些字符集是可用的。可以调用静态的availableCharset方法。使用下面的代码可以确定所有可用的字符集名字：
 * Map<String,Charset> charsets=Charset.availableCharset();
 *
 * 如果某个字符不能表示，它将被转换成？。
 *
 *
 * @author xiaoE
 *
 */
public class CharsetTest {
	public static void main(String[] args) {
		String mystr = "hello 我是中国人！";
		Charset cset = Charset.forName("UTF-8");

		// 编码成字符序列
		ByteBuffer buffer = cset.encode(mystr);
		byte[] bytes = buffer.array();

		// 打印字符序列
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i]);
		}
		System.out.println();

		// 解码字符序列
		ByteBuffer bbuf = ByteBuffer.wrap(bytes, 0, mystr.length() * 2);
		CharBuffer cbuf = cset.decode(bbuf);
		String result = cbuf.toString();
		System.out.println(result);
	}
}
