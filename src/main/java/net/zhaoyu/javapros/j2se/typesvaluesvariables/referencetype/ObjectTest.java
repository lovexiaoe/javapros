package net.zhaoyu.javapros.j2se.typesvaluesvariables.referencetype;

import net.zhaoyu.javapros.j2se.designpattern.structure.proxy.jintai.Object;

/**
 * 所有的Class和数组类型都继承Object类。
 * # clone方法复制一个对象。
 * # equals方法基于值定义对象相等的概念，非基于引用。
 * # finalize在一个对象销毁之前执行。
 * # getClass返回当前对象的Class对象。
 * # getClass 返回的对象为{@code Class<? extends |T|>}，其中T是getClass需要查找的接口或类。是表达式的静态类型擦除。
 *
 * 
 */
public class ObjectTest {

    public static void main(String[] args) {
        ObjectTest test=new ObjectTest();
        Class<?extends ObjectTest> clazz = test.getClass();

    }

    Integer i=10;

    public static void foo() {

    }
}
