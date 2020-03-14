package net.zhaoyu.javapros.j2se.oop.innerclass;

public class StaticInnerClass {

	public static void main(String[] args) {
		// 静态内部类可以 引用外部类名称实例化，而不像其它内部类需要一个外部类的实例。
		// 所以可以在外部类中引用。
		Pair pair = new Pair(1.1, 2.2);
	}

	/**
	 * 只有内部类可以使用static，静态内部类除了不能引用外部类的对象外，和其它所有内部类完全一样。
	 *
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
