package com.zhaoyu.designpattern.j2ee.delegate;

public class EJBService implements BusinessService {

	@Override
	public void doBusiness() {
		System.out.println("���� EJB service ����ҵ���߼���");
	}

}
