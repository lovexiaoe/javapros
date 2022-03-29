package net.zhaoyu.javapros.j2se.expression;

/**
 * 重载也被称为，“迟绑定，自引用“，迟绑定意味着方法调用的引用是在运行时的引用对象，而不是在编译时引用类型。
 * 它是面向对象的重要思想，让语言强支持抽象。
 */
public class OverridingMethodInvokeTest {
    public static void main(String[] args) {
        Point p = new Point();
        System.out.println("p.move(20,20):");
        p.move(20, 20);
        ColoredPoint cp = new ColoredPoint();
        System.out.println("cp.move(20,20):");
        cp.move(20, 20);
        p = new ColoredPoint();
        System.out.println("p.move(20,20), p colored:");
        p.move(20, 20);
    }
}
class Point {
    final int EDGE = 20;
    int x, y;
    void move(int dx, int dy) {
        x += dx; y += dy;
        if (Math.abs(x) >= EDGE || Math.abs(y) >= EDGE)
            clear();
    }
    void clear() {
        System.out.println("\tPoint clear");
        x = 0; y = 0;
    }
}
class ColoredPoint extends Point {
    int color;
    void clear() {
        System.out.println("\tColoredPoint clear");
        super.clear();
        color = 0;
    }
}

/*
p.move(20,20):
	Point clear
cp.move(20,20):
	ColoredPoint clear
	Point clear
p.move(20,20), p colored:
	ColoredPoint clear
	Point clear
 */