package net.zhaoyu.javapros.j2se.basic.innerclass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 局部内部类是定义在一定的代码块中，作用域被限定在声明的代码块中。
 * 不能使用public 或者 private修饰。
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
		// 局部内部类只能在这个代码块中访问
		TimePrint tp = new TimePrint();
	}
}
