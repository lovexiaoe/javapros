package com.zhaoyu.generic.why;

import java.util.ArrayList;

/**
 * ���£���java5֮ǰ����û�м��뷺�ͣ�ArrayList�Ⱦۼ���ֻ��ά��Object���͵�Ԫ�أ�
 * ���ڲ���������Ҫ����ǿ��ת����
 * ���⣬����ӵ�ʱ���������������͵Ķ��󡣶��������������������ж��������
 * �����������ط�ȡ����������ת��ʱ�����п��ܷ����쳣��
 *
 * �����ṩ�˲������ͣ�type parameters���ܺõؽ������һ���⡣
 *
 * @author xiaoE
 *
 */
public class WhyTest {
	public static void main(String[] args) {
		ArrayList li = new ArrayList();
		li.add("���ǵ�һ��Ԫ��");

		// ��Ҫǿ��ת��
		String str = (String) li.get(0);

		// ������������Ԫ��
		int i = 2;
		li.add(i);
	}
}
