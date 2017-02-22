package com.zhaoyu.designpattern.behaviours.responselink;

public class Test {

	public static void main(String[] args) {
		RequestHandler hr = new HRRequestHandle();
		RequestHandler pm = new PRequestHandle(hr);
		RequestHandler tl = new TLRequestHandle(pm);

		// team leader处理离职请求
		Request request = new DimissionRequest();
		tl.handleRequest(request);

		System.out.println("===========");
		// team leader处理加薪请求
		request = new AddMoneyRequest();
		tl.handleRequest(request);

		System.out.println("========");
		// 项目经理上理辞职请求
		request = new DimissionRequest();
		pm.handleRequest(request);
	}
}
