package net.zhaoyu.javapros.j2se.oop.interfaces;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * c中的回调是通过传递方法指针来实现。java中回调是通过传递对象并指定方法来实现的。
 * 被传递的对象需要实现java.awt.event包中的ActionListener接口。 <code>
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
		 * 这里定义了一个每10s显示时间的定时器任务。
		 * awt.Timer是用于执行定时任务的，consumers.Timer是用于调度后台任务。
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