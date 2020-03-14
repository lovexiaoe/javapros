package net.zhaoyu.javapros.j2se.designpattern.j2ee.servicelocator;

public class Service1 implements Service {

	@Override
	public String getName() {
		return "Service1";
	}

	@Override
	public void execute() {
		System.out.println("Executing Service1");
	}

}
