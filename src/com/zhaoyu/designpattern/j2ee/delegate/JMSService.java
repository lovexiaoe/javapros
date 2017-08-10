package com.zhaoyu.designpattern.j2ee.delegate;

public class JMSService implements BusinessService {

	@Override
	public void doBusiness() {
		System.out.println("调用 JMS service 处理业务逻辑。");
	}

}
