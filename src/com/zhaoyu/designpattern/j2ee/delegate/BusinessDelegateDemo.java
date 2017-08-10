package com.zhaoyu.designpattern.j2ee.delegate;

/**
 * client只管调用执行任务，具体执行的任务类型，可以交给托管去处理。
 *
 * @author xiaoE
 *
 */
public class BusinessDelegateDemo {
	public static void main(String[] args) {
		BusinessDelegate businessDelegate = new BusinessDelegate();
		businessDelegate.setServiceType("EJB");

		Client client = new Client(businessDelegate);
		client.doTask();

		businessDelegate.setServiceType("JMS");
		client.doTask();
	}
}
