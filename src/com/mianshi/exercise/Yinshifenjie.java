package com.mianshi.exercise;

/**
 * ��һ���������ֽ������������磺����90,��ӡ��90=2*3*3*5��
 * �����������n���зֽ���������Ӧ���ҵ�һ����С������k��Ȼ������������ɣ�
 * (1)����������ǡ����n����˵���ֽ��������Ĺ����Ѿ���������ӡ�����ɡ�
 * (2)���n <> k����n�ܱ�k��������Ӧ��ӡ��k��ֵ������n����k����,��Ϊ�µ���������n,�ظ�ִ�е�һ����
 * (3)���n���ܱ�k����������k+1��Ϊk��ֵ,�ظ�ִ�е�һ����
 * 
 * @author xiaoE
 *
 */
public class Yinshifenjie {
	public static void main(String[] args) {
		int n = 90;
		int k = 2;
		while (k <= n) {
			if (k == n) {
				System.out.println(n);
				break;
			} else {
				if (n % k == 0) {
					System.out.println(k);
					n = n / k;
				} else {
					k++;
				}
			}

		}
	}
}
