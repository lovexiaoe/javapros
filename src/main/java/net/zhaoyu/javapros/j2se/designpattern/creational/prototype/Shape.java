package net.zhaoyu.javapros.j2se.designpattern.creational.prototype;

public abstract class Shape implements Cloneable {
	private String id;
	protected String type;

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	abstract void draw();

	@Override
	public Object clone() {
		Object clone = null;

		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}
}
