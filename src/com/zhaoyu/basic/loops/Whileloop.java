package com.zhaoyu.basic.loops;

public class Whileloop {
	public static void main(String[] args) {
		int i = 0;
		// while ʹ��break�˳�
		while (true) {
			i++;
			if (i > 3) {
				break;
			}
			System.out.println(i);
		}

		// ʹ��break��ǩ��
		// javaҲ������һ������goto�ķ���������ѭ������ǩ�������ϣ�����������ѭ��֮ǰ����һ��ð�š�
		// ���ַ�������goto�����Բ����ᳫʹ�á�
		read_data: while (true) {
			i++;
			if (i > 6) {
				break read_data;
			}
			int j = 0;
			stop_lebel: while (true) {
				j++;
				System.out.println("--�ڲ�ѭ��");
				if (j < 2) {
					break stop_lebel;
				}
			}
			System.out.println(i);
		}

		// break��ǩҲ����ʹ����if�����
		int k = 0;
		if_tab: if (k == 1) {
			System.out.println("k==0");
		} else {
			System.out.println("if��ʹ����break��ǩ��");
			break if_tab;
		}
	}
}
