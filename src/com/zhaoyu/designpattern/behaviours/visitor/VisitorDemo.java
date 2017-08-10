package com.zhaoyu.designpattern.behaviours.visitor;

public class VisitorDemo {
	public static void main(String[] args) {

		ComputerPart computer = new Computer();
		computer.accept(new ComputerPartDisplayVisitor());
	}
}
