package com.zhaoyu.io.standerdio;

import java.util.Date;

public class Sysout {
	/**
	 * ��׼������Ͱ��� ��f��������s�ַ�����dʮ����������
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 3.3333333333333335 int ��󳤶����
		System.out.println(10.0 / 3.0);

		// ��ʽ��������,��8λ�ַ����ȣ�������С���㣬С������2λС�����ȴ�ӡ
		// ���Ȳ���8λ�ӿո�
		/*
		 * ������
		 * 333333333.32
		 * 33.32
		 */
		System.out.printf("%8.2f", 333333333.323);
		System.out.println();
		System.out.printf("%8.2f", 33.323);
		System.out.println();

		// ���ű�־�����˷���ķָ���������3,333.33
		System.out.printf("%,.2f\n", 10000.0 / 3.0);

		// ���ʱ�� :������ ���� 28 16:45:49 CST 2015
		System.out.printf("%tc\n", new Date());
		// 2015-03-28
		System.out.printf("%tF\n", new Date());
		// �����Ҫ���и�ϸ�������ڲ������ƣ����ǿ���ָ������ʽ���Ĳ����������������������%���棬����$��ֹ��
		// ��������������Ǵ�1��ʼ��
		System.out.printf("%1$s %2$tB %2$te %2$ty", "Due date:", new Date());

	}
}
