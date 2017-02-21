package com.mianshi.exercise;

/**
 * 有n个人围成一圈，顺序排号。从第一个人开始报数（从1到3报数），凡报到3的人退出圈子，继续报数。问最后留下的是原来第几号的那位。
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
				System.out.println("原排在第" + (i + 1) + "位的人留下了。");
			}
		}
	}
}
