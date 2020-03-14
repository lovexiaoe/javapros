package net.zhaoyu.javapros.j2se.designpattern.j2ee.Interceptingfilter;

public class Client {
	FilterManager filterManager;

	public void setFilterManager(FilterManager filterManager) {
		this.filterManager = filterManager;
	}

	public void sendRequest(String request) {
		filterManager.filterRequest(request);
	}
}
