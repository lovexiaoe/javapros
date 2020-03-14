package net.zhaoyu.javapros.j2se.oop.objectdetail;

import java.util.ArrayList;
import java.util.List;

/**
 * ==比较都是栈中的对象是否相等，Object的equals方法使用==来实现。
 * 基础类型直接存放在栈中，==比较的是两个基础类型的值是否相等。
 * 而引用类型在栈中存放的是对堆中地址的引用，所以==默认比较的是引用的地址是否相等。
 * 引用类型继承自Object，equals方法一般都会重写，用于比较两个对象是否相等。如String等类，
 * 集合类中也是通过equals方法比较两个对象的相等性。
 *
 * 所以习惯上使用==比较基本类型字段，使用equals比较引用字段。
 *
 * java语言的equals方法具有以下特性：
 * 1，自反性，非空引用x,x.equals(x) 应该返回true。
 * 2，对称性，非空引用x和y,x.equals(y)返回true,则y.equals(x)也应当返回true.
 * 3，传递性，非空引用x,y,z，如果x.equals(y)返回true，y.equals(z)返回true,x.equals(z)也应该返回true。
 * 4，一致性，如果x,y引用的对象没有发生变化，那么，反复调用x.equals(y)应该返回相同的结果 。
 * 5，对于任意非空引用x，x.equals(null)应该返回false。
 *
 * java重写equals的步骤：如果需要比较的类名为className,将比较参数命名为otherObject,则
 * 1，检测this与otherObject是否引用同一个对象：if(this==otherObject) return true;
 * 这条语句是一种优化，如果 是同一个引用 ，就不需要 进行对比属性了。
 * 2,检测otherObject是否为null，如果为null,返回false。if(null==otherObject) return false;
 * 3,比较otherObject和this是否属于同一类。如果equals的语义在各个子类中有所改变，就使用getClass检测：
 * if(getClass()!=otherObject.getClass)return false;
 * 如果所有的子类都拥有统一的equals定义，就使用instanceof检测。
 * if(!(otherObject instanceof ClassName)) return false。
 * 4,将otherObject转换成相应的类类型变量。
 * className other=(className)otherObject;
 * 5,如果是在子类中重新定义equals，就要在其中包含调用super.equals(other)。
 * 6,使用==比较基本类型字段，使用equals比较引用字段。如果所有的字段都匹配，则返回true,否则，返回false。
 *
 *
 * @author xiaoe
 *
 */
public class EqualsTest {
	public static void main(String[] args) {
		int i=0,j=0;
		double d=1.0;
		boolean b=false;
		System.out.println(i==j);                          //true
		System.out.println(i==d);                          //false
		//System.out.println(i==b);                        //编译错误,不能装换比较。

		//集合中使用equals方法比较对象的相等性。
		List<Integer> ints=new ArrayList<Integer>();
		while(i++<3){
			ints.add(new Integer(2));
		}
		ints.remove(new Integer(2));
		System.out.println(ints.size());                  //新添3个元素，删除1个，还剩2个。

		System.out.println((0.1*3==0.3)+" 0.1*0.3="+(0.1*3));                     //false，0.1*3=0.30000000000000004
		System.out.println((0.1+0.3)==0.2);               //true
		String s="asdf";
		System.out.println(s=="asdf");                     //字符串常量池

		System.out.println("equals是Object的方法。");
	}
}
