package com.mianshi.exercise;

/**
 * ��n����Χ��һȦ��˳���źš��ӵ�һ���˿�ʼ��������1��3��������������3�����˳�Ȧ�ӣ�������������������µ���ԭ���ڼ��ŵ���λ��
 */
public class RenQuanZi {
	public static void main(String[] args) {
		int n = 13;
		boolean[] arr = new boolean[n];
		for (int i = 0; i < n; i++) {
			arr[i] = true;
		}
		int leftNum = n;
		int countNum = 0;
		int index = 0;
		while (leftNum > 1) {
			if (arr[index] == true) {
				countNum++;
				if (countNum == 3) {
					arr[index] = false;
					leftNum--;
					countNum = 0;
				}
			}
			index++;
			if (index == n) {
				index = 0;
			}
		}
		for (int i = 0; i < n; i++) {
			if (arr[i] == true) {
				System.out.println("ԭ���ڵ�" + (i + 1) + "λ���������ˡ�");
			}
		}
	}
}
