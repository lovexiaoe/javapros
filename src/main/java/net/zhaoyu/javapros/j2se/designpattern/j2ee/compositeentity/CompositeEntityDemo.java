package net.zhaoyu.javapros.j2se.designpattern.j2ee.compositeentity;

public class CompositeEntityDemo {
	public static void main(String[] args) {

		Client client = new Client();
		client.setData("TestStampedLock", "Data");
		client.printData();
		client.setData("Second TestStampedLock", "Data1");
		client.printData();
	}
}
