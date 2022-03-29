package net.zhaoyu.javapros.j2se.basic.oop.execution;

/**
 * 构造方法的执行过程
 * 1. 当ColoredPoint被创建时，首先为新的ColoredPoint分配空间，包含了x,y,color字段，这些字段会被赋值为默认值。
 * 2. ColoredPoint的无参构造方法会被调用。
 *      {@code ColoredPoint() { super(); }}
 *    这个方法又调用了父级的构造方法。即Point的无参构造方法。
 *      {@code Point() { super(); x = 1; y = 1; }}
 *    有super方法，Object的无参构造方法被调用。
 *      {@code Object();}
 * 3. Object没有父类，则递归结束。
 * 4. 接着，执行Point的实例变量初始化，x和y没有提供初始化的表达式。所以这步不需要执行任何动作。然后，Point的构造方法被执行，x和y都变为1。
 * 5. 执行ColoredPoint的实例变量初始化，即执行color=0xFF00FF，然后执行ColoredPoint的构造方法，完成整个过程。
 */
public class ConstructorExecutionTest {
    public static void main(String[] args) {
        ColoredPoint cp = new ColoredPoint();
        System.out.println(cp.color); //16711935
    }
}
class Point{
    int x,y;
    Point(){
        x=1;y=1;
    }
}
class ColoredPoint extends Point{
    int color=0xFF00FF;
}
