package net.zhaoyu.javapros.j2se.designpattern.j2ee.servicelocator;

public class Service2 implements Service {

	@Override
	public String getName() {
		return "Service2";
	}

	@Override
	public void execute() {
		System.out.println("Executing Service2");
	}

}
