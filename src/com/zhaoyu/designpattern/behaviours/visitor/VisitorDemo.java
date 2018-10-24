package com.zhaoyu.designpattern.behaviours.visitor;


/**
 * 这个模式可以理解为某个由多个部分构成的组织（并且这个组织的结构比较固定，基本不会变动），
 *  每个部分针对不同的访问者，产生不同的动作。
 * 	例如，针对学校开学报名缴费，那么可以分为（交学费，住宿费，书本费）
 * 访问者（复读生，走读生，新生）对每个部门的动作不一样，复读生可能需要交高额的学费，
 * 而走读生不需要交住宿费，而新生可能各个缴费点都需要跑一趟。这种情况就是和与访问者模式。
 */
public class VisitorDemo {
	public static void main(String[] args) {

		ComputerPart computer = new Computer();
		computer.accept(new ComputerPartDisplayVisitor());
	}
}
