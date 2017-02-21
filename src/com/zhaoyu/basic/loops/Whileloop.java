package com.zhaoyu.basic.loops;

public class Whileloop {
	public static void main(String[] args) {
		int i = 0;
		// while 使用break退出
		while (true) {
			i++;
			if (i > 3) {
				break;
			}
			System.out.println(i);
		}

		// 使用break标签。
		// java也定义了一种类似goto的方法来跳出循环，标签必须放在希望跳出的外层循环之前，跟一个冒号。
		// 这种方法类似goto，所以并不提倡使用。
		read_data: while (true) {
			i++;
			if (i > 6) {
				break read_data;
			}
			int j = 0;
			stop_lebel: while (true) {
				j++;
				System.out.println("--内层循环");
				if (j < 2) {
					break stop_lebel;
				}
			}
			System.out.println(i);
		}

		// break标签也可以使用在if语句中
		int k = 0;
		if_tab: if (k == 1) {
			System.out.println("k==0");
		} else {
			System.out.println("if中使用了break标签。");
			break if_tab;
		}
	}
}
