package com.mianshi.exercise;

/**
 * ��ӡ�����е� "ˮ�ɻ��� "����ν "ˮ�ɻ��� "��ָһ����λ�������λ���������͵��ڸ�������
 * ���磺153��һ�� "ˮ�ɻ��� "����Ϊ153=1�����η���5�����η���3�����η���
 * 
 * @author xiaoE
 *
 */
public class Shuixianhua {
	public static void main(String[] args) {
		int l, m, n;
		for (int i = 100; i < 1000; i++) {
			l = i / 100;
			m = (i % 100) / 10;
			n = i % 10;
			if (i == l * l * l + m * m * m + n * n * n) {
				System.out.println(i);
			}
		}
	}
}
