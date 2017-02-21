package com.zhaoyu.oop.innerclass;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.zhaoyu.oop.innerclass.TalkingClock.TimePrinter;

/**
 * ���潫timeprinter����ΪTalkingClock��һ���ڲ��ࡣ
 *
 * @author xiaoE
 *
 */
public class InnerClassTest {
	public static void main(String[] args) {
		TalkingClock tc = new TalkingClock(1000, true);
		tc.start();

		JOptionPane.showMessageDialog(null, "Quit Program?");
		System.exit(0);

		// �ⲿ��timePrinter��ʹ�á�
		TimePrinter tp = tc.new TimePrinter();
	}
}

class TalkingClock {
	private int interval;
	private boolean beep;

	public TalkingClock(int interval, boolean beep) {
		super();
		this.interval = interval;
		this.beep = beep;
	}

	public void start() {
		ActionListener timeprinter = new TimePrinter();
		Timer timer = new Timer(interval, timeprinter);
		timer.start();
	}

	/**
	 * ������һ���ڲ��࣬�ڲ�����Է�������ı�����Ҳ����ֱ�ӷ��������ⲿ��ı�������beep��
	 *
	 * @author xiaoE
	 *
	 */
	public class TimePrinter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Date now = new Date();
			System.err.println("at the tone,the time is " + now);
			// �����beep��ʾ�˶��ⲿ��beep�����ã�Ҳ����дΪTalkingClock.this.beep��
			// OuterClass.this���ڲ�����ⲿ������á�
			if (beep) {
				Toolkit.getDefaultToolkit().beep();
			}
		}

	}
}
