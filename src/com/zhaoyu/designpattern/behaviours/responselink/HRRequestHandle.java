package com.zhaoyu.designpattern.behaviours.responselink;

import javax.servlet.http.HttpServletRequest;

public class HRRequestHandle implements RequestHandle {

	public void handleRequest(HttpServletRequest request) {
		if (request instanceof DimissionRequest) {
			System.out.println("Ҫ��ְ, ��������!");
		}

		System.out.println("������*");
	}
}
