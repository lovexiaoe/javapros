package com.zhaoyu.designpattern.behaviours.responselink;

public class Test {

	public static void main(String[] args) {
		RequestHandler hr = new HRRequestHandle();
		RequestHandler pm = new PRequestHandle(hr);
		RequestHandler tl = new TLRequestHandle(pm);

		// team leader������ְ����
		Request request = new DimissionRequest();
		tl.handleRequest(request);

		System.out.println("===========");
		// team leader�����н����
		request = new AddMoneyRequest();
		tl.handleRequest(request);

		System.out.println("========");
		// ��Ŀ���������ְ����
		request = new DimissionRequest();
		pm.handleRequest(request);
	}
}
