package net.zhaoyu.javapros.j2se.designpattern.j2ee.delegate;

public class JMSService implements BusinessService {

	@Override
	public void doBusiness() {
		System.out.println(" JMS service design pattern");
	}

}
