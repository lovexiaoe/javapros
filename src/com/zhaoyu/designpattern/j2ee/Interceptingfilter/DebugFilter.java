package com.zhaoyu.designpattern.j2ee.Interceptingfilter;

public class DebugFilter implements Filter {

	@Override
	public void execute(String request) {
		System.out.println("request log: " + request);
	}

}
