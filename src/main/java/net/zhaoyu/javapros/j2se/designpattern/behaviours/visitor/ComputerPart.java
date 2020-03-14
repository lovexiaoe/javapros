package net.zhaoyu.javapros.j2se.designpattern.behaviours.visitor;

public interface ComputerPart {
	public void accept(ComputerPartVisitor computerPartVisitor);
}
