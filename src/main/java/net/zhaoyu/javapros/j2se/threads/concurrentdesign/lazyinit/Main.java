package net.zhaoyu.javapros.j2se.threads.concurrentdesign.lazyinit;

/**
 * 使用线程安全的懒加载，可以看到这些线程创建的都是一个connect.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i=0; i<20; i++){
			Task task=new Task();
			Thread thread=new Thread(task);
			thread.start();
		}

	}

}
