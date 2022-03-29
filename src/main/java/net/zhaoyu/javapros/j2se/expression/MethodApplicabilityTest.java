package net.zhaoyu.javapros.j2se.expression;

/**
 * 方法的适用性，方法调用时，会按照参数的字面类型调用方法。
 */
public class MethodApplicabilityTest{
    public static void main(String[] args) {
        ColoredPoint1 cp = new ColoredPoint1();
        byte color = 37;
        cp.setColor(color);

        /*
        下面的调用，提供了byte类型参数的方法，但是调用的字面类型是int，
        37小于byte的最大范围，这种在赋值转换中是允许的，但是在方法调用中不能被转换。
        如果提供了long型的方法，则会做拓宽转化调用，这样是允许的。
         */
//        cp.setColor(37);// compile-time error，编译错误，


        ColoredPoint cp1 = new ColoredPoint();
        /*
            有两个适用的test方法都可以调用，这时的调用时模糊不清的，所以会报错。
         */
//        test(cp1, cp1); // compile-time error



        ColoredPoint cp2 = new ColoredPoint();
        /*
            这里调用test1，最精确的调用是test1(ColoredPoint p)，但是返回值是int,所以会报错。
            按照参数找到最精确的调用后，不会再扩展寻找test1(Point p)。
         */
//        String s = test1(cp2); // compile-time error

        
    }

    static void test(ColoredPoint p, Point q) {
        System.out.println("(ColoredPoint, Point)");
    }
    static void test(Point p, ColoredPoint q) {
        System.out.println("(Point, ColoredPoint)");
    }

    static int test1(ColoredPoint p) {
        return p.color;
    }
    static String test1(Point p) {
        return "Point";
    }
}
class ColoredPoint1 {
    int x, y;
    byte color;
    void setColor(byte color) { this.color = color; }
}