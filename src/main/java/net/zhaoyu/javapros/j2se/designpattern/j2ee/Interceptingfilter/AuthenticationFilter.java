package net.zhaoyu.javapros.j2se.designpattern.j2ee.Interceptingfilter;

public class AuthenticationFilter implements Filter {

	@Override
	public void execute(String request) {
		System.out.println("Authentication request: " + request);
	}

}
