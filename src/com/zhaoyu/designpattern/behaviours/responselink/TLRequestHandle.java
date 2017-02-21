package com.zhaoyu.designpattern.behaviours.responselink;

import javax.servlet.http.HttpServletRequest;

public class TLRequestHandle implements RequestHandler {

	RequestHandler rh;

	public TLRequestHandle(RequestHandler rh) {
		this.rh = rh;
	}

	public void handleRequest(HttpServletRequest request) {
		if (request instanceof LeaveRequest) {
			System.out.println("Ҫ���, ��Ŀ�鳤����!");
		} else {
			rh.handlerRequest(request);
		}
	}
}
