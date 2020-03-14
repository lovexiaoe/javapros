package net.zhaoyu.javapros.j2se.designpattern.behaviours.visitor;


/**
 * 访问者模式中，对于某个对象或者一组对象，不同的访问者，产生不同的结果，执行操作也不同，就可以使用访问者模式。
 * 如：对，步兵，骑兵，弓箭手，这一组对象，attackVisitor访问时，进攻，而retreatVisitor访问时撤退。
 *
 * 访问者模式中包括了，组件，组件接口，访问者，访问者接口。对于不同的组件的访问动作，定义在访问者中，组件只需要
 * accept访问者的访问，并在accept中处理访问者的动作。
 *
 * 访问者模式将数据接口（组件）和数据操作（访问动作）分离，
 */
public class VisitorDemo {
	public static void main(String[] args) {

		ComputerPart computer = new Computer();
		computer.accept(new ComputerPartDisplayVisitor());
	}
}
