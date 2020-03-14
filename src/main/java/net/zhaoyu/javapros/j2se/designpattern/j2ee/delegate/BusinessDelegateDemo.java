package net.zhaoyu.javapros.j2se.designpattern.j2ee.delegate;

/**
 * clientֻ�ܵ���ִ�����񣬾���ִ�е��������ͣ����Խ����й�ȥ����
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
