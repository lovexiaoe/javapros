package net.zhaoyu.javapros.j2se.designpattern.behaviours.memento;

public class Memento {
	private String state;

	public Memento(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}
