package net.zhaoyu.javapros.mianshi.exercise;

/**
 * 古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第二个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
 * 菲波拉契数列问题
 *
 * @author xiaoE
 *
 */
public class Tuzi {
	public static void main(String[] args) {
		System.out.println("第一个月的兔子对数为：" + 1);
		System.out.println("第二个月的兔子对数为：" + 1);
		// 下个月能产生的兔子
		int i = 1;
		// 兔子总数
		int j = 1;
		int s;
		for (int l = 3; l <= 20; l++) {
			// 记录上个月兔子总数，即可产生幼仔的数目。
			s = j;
			// 这个月兔子总数是上个月兔子总数 +这个月生产的幼仔。
			j = i + j;
			// 下个月能产生的幼仔为上个月兔子总数。
			i = s;

			System.out.println("第" + l + "个月 " + "i-j: " + i + "-" + j);
			System.out.println("第" + l + "个月兔子对数为：" + j);
		}

	}

}
