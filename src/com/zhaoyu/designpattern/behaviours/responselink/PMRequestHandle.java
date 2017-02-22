package com.zhaoyu.designpattern.behaviours.responselink;

import org.apache.catalina.connector.Request;

public class PMRequestHandle implements RequestHandler {

	RequestHandler rh;

	public PMRequestHandle(RequestHandler rh) {
		this.rh = rh;
	}

	public void handlerRequest(Request request) {
		if (request instanceof AddMoneyRequest) {
			System.out.println("要加薪, 项目经理审批!");
		} else {
			rh.handlerRequest(request);
		}
	}
}
