package com.zhaoyu.oop.innerclass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * �ֲ��ڲ����Ƕ�����һ���Ĵ�����У��������޶��������Ĵ�����С�
 * ����ʹ��public ���� private���Ρ�
 *
 * @author xiaoE
 *
 */
public class LocalInnerClass {
	public static void main(String[] args) {
		class TimePrint implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		}
		// �ֲ��ڲ���ֻ�������������з���
		TimePrint tp = new TimePrint();
	}
}
