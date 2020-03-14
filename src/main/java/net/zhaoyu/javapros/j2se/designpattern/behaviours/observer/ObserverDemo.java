package net.zhaoyu.javapros.j2se.designpattern.behaviours.observer;


/**
 * 观察者模式有目标和观察者，1，观察者需要注册到目标通知的队列中。2，当目标发生变化时，触发所有注册的
 * 观察者的更新操作。有些类似于发布订阅模式，但是发布订阅模式中，订阅者将需要订阅的事件注册到调度中心，
 * 发布者并不会直接触事件到订阅者。这便是不同之处。
 */
public class ObserverDemo {
	public static void main(String[] args) {
		Subject subject = new Subject();

		new HexObserver(subject);
		new OctalObserver(subject);
		new BinaryObserver(subject);

		System.out.println("First state change: 15");
		subject.setState(15);
		System.out.println("Second state change: 10");
		subject.setState(10);
	}
}
