package net.zhaoyu.javapros.mianshi.exercise;

/**
 * 打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。
 * 例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。
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
