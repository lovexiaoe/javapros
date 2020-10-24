package net.zhaoyu.javapros.j2se.threads.concurrentdesign.shortlock;

import java.util.concurrent.TimeUnit;

public class Operations {
	
	public static void readData(){
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeData(){
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void processData(){
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
