package com.zhaoyu.designpattern.abstractfactory;

/**
 * ���󹤳��ǹ���ģʽ����չ�档ʹ����ģʽӵ�����������Ʒ��������������ģʽ�Ľ��ɵõ����󹤳�ģʽ��
 * �����ܽ᣺���󹤳��������ͺͿϵ»������ȣ������������ȶ������ɡ���ȥ�����ܳԵ����ȣ�ȥ�ϵ»�Ҳ�ǡ�
 * 
 * @author zhaoyu
 * 
 */
public class AbstractFactoryTest {
	public static void main(String[] args) {
		// ��KFC�����cola
		AbstractFactory abstractFactory = new KFC();

		Cola cola = abstractFactory.genCola();
		cola.drink();
	}
}
