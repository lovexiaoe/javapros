package com.zhaoyu.oop.interfaces;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * c�еĻص���ͨ�����ݷ���ָ����ʵ�֡�java�лص���ͨ�����ݶ���ָ��������ʵ�ֵġ�
 * �����ݵĶ�����Ҫʵ��java.awt.event���е�ActionListener�ӿڡ� <code>
 * 		public interface ActionListener
 * 		{
 * 			void actionPerformed(ActionEvent event);
 * 		}
 * </code>
 *
 * @author xiaoE
 *
 */
public class ActionListenerInterface {
	public static void main(String[] args) {
		ActionListener listener = new TimePrinter();

		/*
		 * ���ﶨ����һ��ÿ10s��ʾʱ��Ķ�ʱ������
		 * awt.Timer������ִ�ж�ʱ����ģ�util.Timer�����ڵ��Ⱥ�̨����
		 */
		Timer t = new Timer(10000, listener);
		t.start();
		JOptionPane.showMessageDialog(null, "Quit Program?");
		System.exit(0);
	}
}

class TimePrinter implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Date now = new Date();
		System.err.println("at the tone,the time is " + now);
		Toolkit.getDefaultToolkit().beep();
	}

}