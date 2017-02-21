package com.zhaoyu.designpattern.factory;

/**
 * ����ģʽ ����Թ���ֻ����ĳһ�ֲ�Ʒ�����Σ� ��Ь������Ь�ӣ�nakeЬ������nakeЬ��AddidasЬ����AddidasЬ��
 * ����ģʽ��ӿ���ԭ�򣨶���չ���ţ����޸ķ�գ� ������������һ��ֻ��һ������������һ�ֲ�Ʒ��
 * ���ʹ���������������ֲ�Ʒ����������Ľ��������Ȥ�ط��֣������ɳ��󹤳�ģʽ��
 * 
 * @author zhaoyu
 * 
 */
public class FactoryTest {

	public static void main(String[] args) {
		// ��nakeЬ
		Factory nakeFactory = new NakeFactory();
		Shoe shoe = nakeFactory.generateShoe();
		System.out.println(shoe.getShoeName());

		// ��addidasЬ
		Factory addidasFactory = new AddidasFactory();
		Shoe shoe1 = addidasFactory.generateShoe();
		System.out.println(shoe1.getShoeName());
	}

}
