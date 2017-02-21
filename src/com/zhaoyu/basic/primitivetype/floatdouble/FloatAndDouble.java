package com.zhaoyu.basic.primitivetype.floatdouble;

/**
 * ��������˵��float��double���͵����֪ʶ��ʹ�á�
 * flat���͵���ֵ����һ��F(�磺3.402F),û�к�׺F�ĸ�����ֵĬ��Ϊdouble���͡�double����Ҳ���� �ں������D(��3.402D)��
 *
 * float
 * ��java��ʹ��4�ֽڱ�ʾ��ȡֵ��Χ��+-3.40282347E+38F E+38��ʾ����10��38�η���e-28��ʾ����10��-28�η���
 * ����float���ͱ�ʾ�ľ������ޣ�ֻ��8-9λ�ľ��ȣ�����һ�㶼����float���ͣ�ʹ��double��
 *
 * double ��java��ʹ��8�ֽ�����ʾ ����ʾ������float���ȵ�������
 *
 * ��jdk5�У����� ʹ��ʮ����������ʾ������ֵ����0.125���Ա�ʾ��0x1.0p-3����ʮ����������p��ʾָ����������e
 * β������16���ƣ�ָ������10���ƣ�ָ���Ļ�����2��������10��
 *
 * ����ʹ��Double.isNaN(x)�����ж�x�Ƿ���һ���������͵�ֵ ��
 *
 * @author xiaoe
 *
 */
public class FloatAndDouble {
	public static void main(String[] args) {
		Float f1 = 1.2812312323E20F;
		double f2 = 1281231232.3e29;
		System.out.println(f1.toString());
		System.out.println(String.valueOf(f2));
		System.out.println(String.valueOf("max:" + f1.MAX_VALUE));
		System.out.println(String.valueOf("min:" + f1.MIN_VALUE));
	}
}

/*
 * ��������
 * 1.2812313E20
 * 1.2812312323E38
 * max:3.4028235E38
 * min:1.4E-45
 */
