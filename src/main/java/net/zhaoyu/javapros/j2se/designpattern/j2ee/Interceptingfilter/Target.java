package net.zhaoyu.javapros.j2se.designpattern.j2ee.Interceptingfilter;

public class Target {
	public void execute(String request) {
		System.out.println("Executing request:" + request);
	}
}
