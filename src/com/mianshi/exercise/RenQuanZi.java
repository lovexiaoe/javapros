package com.mianshi.exercise;

/**
 * 这是一个数学中约瑟夫环的问题，这里的解决方法是使用循环解决。
 * 有n个人围成一圈，顺序排号。从第一个人开始报数（从1到3报数），凡报到3的人退出圈子，继续报数。问最后留下的是原来第几号的那位。
 * 上面的方案在只剩很少的元素时，并且报数数字很大的时候，浪费很多性能，如只剩两个人的时候，找出报数到1w的时剩下的人，会循环很多次。
 * 还可以使用数学的方法简便的解决。
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

	/**
	 * 数学方法解决约瑟夫环的问题，N为总人数，M为报数数字。
	 * f(1)=0
	 * f(N)=[f(N-1)+M]%N
	 * 推到过程参考 https://blog.csdn.net/qq_25973267/article/details/50405616
	 */
	public static int josephus(int n,int m){
		int s=0;
		for (int i=2;i<=n;i++){
			s=(s+m)%i;
		}
		return s;
	}
}

