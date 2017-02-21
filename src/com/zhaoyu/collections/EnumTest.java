package com.zhaoyu.collections;

public class EnumTest {
	public static void main(String[] args) {

		String str1 = "LARGE";
		// valueOf��toString���淽����
		Size size = Enum.valueOf(Size.class, str1);
		System.out.println("size=" + size);
		System.out.println("abbreviation=" + size.getAbbreviation());

		// �ڸ�ö�ٶ���һ����̬����values������һ������ȫ��ö�ٵ����顣
		Size[] values = Size.values();
		System.out.println(values[1]);

		// ordinal�������س�����enum�����е�λ�ã���0��ʼ������
		System.out.println(Size.SMALL.ordinal());
	}
}

enum Size {
	SMALL("S"), MEDIUM("M"), LARGE("L");
	// ��;����Զ����Լ���ö�٣����캯����Ҫ����Ϊ˽�С�����S,M,L���������Զ��������abbreviation.
	private Size(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	// abbreviation��д����д��
	private String abbreviation;
}
