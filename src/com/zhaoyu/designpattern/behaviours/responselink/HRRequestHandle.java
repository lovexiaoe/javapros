package com.zhaoyu.designpattern.behaviours.responselink;

import javax.servlet.http.HttpServletRequest;

public class HRRequestHandle implements RequestHandle {

	public void handleRequest(HttpServletRequest request) {
		if (request instanceof DimissionRequest) {
			System.out.println("要离职, 人事审批!");
		}

		System.out.println("请求完*");
	}
}
