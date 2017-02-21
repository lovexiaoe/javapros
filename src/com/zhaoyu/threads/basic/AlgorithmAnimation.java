package com.zhaoyu.threads.basic;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 这个算法动画主要说明了同步器中的信号量的使用，
 * 同步器还有其它的类型如：
 *
 * CyclicBarrier 障栅
 * CountDownLatch 倒计时门栓
 * Exchanger 交换器
 * Semaphore 信号量
 * SynchronousQueue 同步队列
 *
 * 这些具体的同步器类，可参考java核心技术14.10。
 * 
 * @author xiaoE
 *
 */
public class AlgorithmAnimation {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new AnimationFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * 该组件绘制一个数组，并在数组中标记其中两个元素。
 *
 * @author xiaoE
 *
 */
class ArrayComponent extends JComponent {
	private Double marked1;
	private Double marked2;
	private Double[] values;

	/**
	 * 设置绘画的值，用于在排序线程中调用。
	 *
	 * @param values
	 * @param marked1
	 * @param marked2
	 */
	public synchronized void setValues(Double[] values, Double marked1, Double marked2) {
		this.values = values.clone();
		this.marked1 = marked1;
		this.marked2 = marked2;
		repaint();
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		if (values == null) {
			return;
		}
		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth() / values.length;
		for (int i = 0; i < values.length; i++) {
			double height = values[i] * getHeight();
			Rectangle2D bar = new Rectangle2D.Double(width * i, 0, width, height);
			if (values[i] == marked1 || values[i] == marked2) {
				g2.fill(bar);
			} else {
				g2.draw(bar);
			}
		}
	}
}

/**
 * 该Runnable执行一个排序算法，当两个元素进行比较时，算法更新一个组件。
 *
 * @author xiaoE
 *
 */
class Sorter implements Runnable {
	private Double[] values;
	private ArrayComponent component;
	/**
	 * 信号量：一个信号量管理许多的许可证（permits）。信号量没有实际的许可证对象 ，仅维护一个计数。线程通过调用acquire请求许可。
	 * 许可的数量是固定的，由此限制了线程的数量 。其它线程可以通过调用release释放许可。
	 * Semaphone 允许线程集等待，直到被允许继续运行为止。限制访问资源的线程总数，如果许可数是1，
	 * 线程阻塞直到另一个线程给出许可。
	 */
	private Semaphore gate;
	private static final int DELAY = 1000;
	private volatile boolean run;
	private static final int VALUES_LENGTH = 30;

	public Sorter(ArrayComponent comp) {
		values = new Double[VALUES_LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = new Double(Math.random());
		}
		this.component = comp;
		this.gate = new Semaphore(1);
		this.run = false;
	}

	// 使用信号量控制运行
	public void setRun() {
		run = true;
		gate.release();
	}

	// 使用信号量控制单步运行。
	public void setStep() {
		run = false;
		gate.release();
	}

	@Override
	public void run() {
		Comparator<Double> com = new Comparator<Double>() {
			@Override
			public int compare(Double i1, Double i2) {
				component.setValues(values, i1, i2);
				try {
					if (run) {
						Thread.sleep(DELAY);
					} else {
						// 信号量获取
						gate.acquire();
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				return i1.compareTo(i2);
			}
		};
		Arrays.sort(values, com);
		component.setValues(values, null, null);
	}

}

class AnimationFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 300;

	public AnimationFrame() {
		ArrayComponent comp = new ArrayComponent();
		add(comp, BorderLayout.CENTER);

		final Sorter sorter = new Sorter(comp);

		JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sorter.setRun();
			}
		});

		JButton stepButton = new JButton("Step");

		stepButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sorter.setStep();
			}
		});

		JPanel buttons = new JPanel();
		buttons.add(runButton);
		buttons.add(stepButton);
		add(buttons, BorderLayout.NORTH);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		Thread t = new Thread(sorter);
		t.start();
	}
}
