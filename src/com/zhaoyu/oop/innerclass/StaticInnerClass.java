package com.zhaoyu.oop.innerclass;

public class StaticInnerClass {

	public static void main(String[] args) {
		// ��̬�ڲ������ �����ⲿ������ʵ�����������������ڲ�����Ҫһ���ⲿ���ʵ����
		// ���Կ������ⲿ�������á�
		StaticInnerClass.Pair pair = new StaticInnerClass.Pair(1.1, 2.2);
	}

	/**
	 * ֻ���ڲ������ʹ��static����̬�ڲ�����˲��������ⲿ��Ķ����⣬�����������ڲ�����ȫһ����
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
		// ���Pair�ڲ���û����static���Σ�����ᱨ��
		return new Pair(min, max);
	}
}
