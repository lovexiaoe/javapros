package com.zhaoyu.oop.innerclass;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.zhaoyu.oop.innerclass.TalkingClock.TimePrinter;

/**
 * 下面将timeprinter构造为TalkingClock的一个内部类。
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

		// 外部对timePrinter的使用。
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
	 * 这里是一个内部类，内部类可以访问自身的变量，也可以直接访问它的外部类的变量。如beep。
	 *
	 * @author xiaoE
	 *
	 */
	public class TimePrinter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Date now = new Date();
			System.err.println("at the tone,the time is " + now);
			// 这里的beep表示了对外部类beep的引用，也可以写为TalkingClock.this.beep。
			// OuterClass.this是内部类对外部类的引用。
			if (beep) {
				Toolkit.getDefaultToolkit().beep();
			}
		}

	}
}
