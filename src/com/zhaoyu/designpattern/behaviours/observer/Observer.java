package com.zhaoyu.designpattern.behaviours.observer;

public abstract class Observer {
	protected Subject subject;

	public abstract void update();
}
