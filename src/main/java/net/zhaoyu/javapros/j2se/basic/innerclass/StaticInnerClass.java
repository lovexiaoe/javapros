package net.zhaoyu.javapros.j2se.basic.innerclass;

public class StaticInnerClass {

	public static void main(String[] args) {
		// 静态内部类可以直接在外部引用。
		Pair pair = new Pair(1.1, 2.2);
	}

	/**
	 * 静态内部类，也称为静态嵌套类。是声明为static的嵌套类，其作用如同顶级类。
	 *
	 * @author xiaoE
	 *
	 */
	public static class Pair {
		private double min;
		private double max;

		public double getMin() {
			return min;
		}

		public void setMin(double min) {
			this.min = min;
		}

		public double getMax() {
			return max;
		}

		public void setMax(double max) {
			this.max = max;
		}

		public Pair(double min, double max) {
			this.min = min;
			this.max = max;
		}

	}

	public static Pair minmax(double[] values) {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (double d : values) {
			if (min > d) {
				min = d;
			}
			if (max < d) {
				max = d;
			}
		}
		// 如果Pair内部类没有用static修饰，这里会报错。
		return new Pair(min, max);
	}
}
