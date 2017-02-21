package com.zhaoyu.io.charsets;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * javase 1.4�������java.nio������Charset��ͳһ���ַ�������ת����
 * ����ͨ��forName���������һ��Charset��ֻ���������������һ���ٷ����ֻ�������ĳ��������
 * Charset cset=Charset.forName(��ISO-8859-1��);
 * �ַ��������Ǵ�Сд�����еġ�ÿ���ַ����������кܶ���������磺ISO-8859-1�ı����У�
 * ISO8859-1
 * ISO_8859_1
 * 8859-1
 * latin1
 * ����
 * aliases�������Է����ɱ������ɵ�Set����
 * Set<String> aliases=cset.aliases();
 *
 * Ϊ��ȷ��ĳ���ض���������Щ�ַ����ǿ��õġ����Ե��þ�̬��availableCharset������ʹ������Ĵ������ȷ�����п��õ��ַ������֣�
 * Map<String,Charset> charsets=Charset.availableCharset();
 *
 * ���ĳ���ַ����ܱ�ʾ��������ת���ɣ���
 *
 *
 * @author xiaoE
 *
 */
public class CharsetTest {
	public static void main(String[] args) {
		String mystr = "hello �����й��ˣ�";
		Charset cset = Charset.forName("UTF-8");

		// ������ַ�����
		ByteBuffer buffer = cset.encode(mystr);
		byte[] bytes = buffer.array();

		// ��ӡ�ַ�����
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i]);
		}
		System.out.println();

		// �����ַ�����
		ByteBuffer bbuf = ByteBuffer.wrap(bytes, 0, mystr.length() * 2);
		CharBuffer cbuf = cset.decode(bbuf);
		String result = cbuf.toString();
		System.out.println(result);
	}
}
