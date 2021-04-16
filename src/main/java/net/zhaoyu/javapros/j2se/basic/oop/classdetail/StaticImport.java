package net.zhaoyu.javapros.j2se.basic.oop.classdetail;

//静态导入直接导入类的静态方法和静态变量，使用时就不需要加类名
import static java.lang.System.*;

/**
 * 此例用于说明静态导入的使用
 * 
 * @author xiaoe
 *
 */
public class StaticImport {
	public static void main(String[] args) {
		out.print("静态导入不需要加System");
	}
}
