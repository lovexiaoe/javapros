package com.zhaoyu.designpattern.behaviours.responselink;

import org.apache.catalina.connector.Request;

public class PMRequestHandle implements RequestHandler {

	RequestHandler rh;

	public PMRequestHandle(RequestHandler rh) {
		this.rh = rh;
	}

	public void handlerRequest(Request request) {
		if (request instanceof AddMoneyRequest) {
			System.out.println("Ҫ��н, ��Ŀ��������!");
		} else {
			rh.handlerRequest(request);
		}
	}
}
