package com.zhaoyu.designpattern.j2ee.delegate;

public class EJBService implements BusinessService {

	@Override
	public void doBusiness() {
		System.out.println("调用 EJB service 处理业务逻辑。");
	}

}
