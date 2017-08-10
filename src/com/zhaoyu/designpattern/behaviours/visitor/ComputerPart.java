package com.zhaoyu.designpattern.behaviours.visitor;

public interface ComputerPart {
	public void accept(ComputerPartVisitor computerPartVisitor);
}
