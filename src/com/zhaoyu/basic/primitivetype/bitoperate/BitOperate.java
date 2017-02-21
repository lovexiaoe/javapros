package com.zhaoyu.basic.primitivetype.bitoperate;

/**
 * ����ʱ������λ���䣬��λ��0
 * ����ʱ��������λ��0������������ʱ�����Բ������ʽ�����ģ��ƶ�ʱ��λ������λ���ƶ����-1ȡ����
 * �޷���λ����ʱ����λ����0���������޷��������л����������������Ԥ�ڣ��һ�δ�о�������ôִ�еģ�
 * ������������塣
 *
 * ����������intʱ��λ��������Ҳ�Ĳ���Ҫ����ģ32�����㡣��1<<35��1<<3��8����ͬ�ġ�
 * ����������longʱ��Ҫ����ģ64������
 *
 * @author xiaoe
 */
public class BitOperate {
	public static void main(String[] args) {
		System.out.println("��2����3λ�ã�" + (2 << 3));
		System.out.println("��-2����3λ�ã�" + (-2 << 3));
		System.out.println("��3����1λ�ã�" + (3 >> 1));
		// �������������Բ������ƶ��ģ������ƶ�����-1ȡ�����õ���ֵ��
		System.out.println("��-6����1λ�ã�" + (-6 >> 1));
		System.out.println("��-5����2λ�ã�" + (-5 >> 2));
		System.out.println("��-7����3λ�ã�" + (-7 >> 3));
		System.out.println("��-2����3λ�ã�" + (-2 >> 3));
		System.out.println("��4�޷�������3λ�ã�" + (4 >>> 3));
		System.out.println("��-4�޷�������1λ�ã�" + (-4 >>> 1));
		System.out.println("ȡ13�Ķ�����λ�ĵ�������λ��" + shadowBit8(13));
	}

	/**
	 * ����λ������ȡ�ö�����λ�ĵ�������λ��
	 *
	 * @param n
	 * @return
	 */
	static int shadowBit8(int n) {
		return (n & 8) / 8;
		// Ҳ����д������ return (n&(1<<3))>>3;
	}
}
