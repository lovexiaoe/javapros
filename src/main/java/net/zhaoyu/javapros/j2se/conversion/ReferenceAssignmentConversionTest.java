package net.zhaoyu.javapros.j2se.conversion;

/**
 * 引用类型赋值转换的举例说明
 */
public class ReferenceAssignmentConversionTest {
    public static void main(String[] args) {
        Point p = new Point();
        p = new Point3D(); //OK,3D点也是一个2D点。
//        Point3D p3d=p; //编译错误，因为p可能只是一个2D点（即使现在实际上是一个3D）。所以需要转换。

        ColoredPoint cp=new ColoredPoint();
        Colorable c=cp; //OK，cp实现了Colorable接口。

        int[] a = new int[4];
        byte[] b = new byte[4];
//        a=b; //编译错误，不同基础类型的数组是不相同的。

        Point3D[] p3da = new Point3D[3];
        Point[] pa=p3da; //OK，因为我一个将一个Point3D赋值给Point。
//        p3da=pa; //反之则错误。我们不能将一个Point赋值给Point3D。
    }
}

class Point {
    int x,y;
}

class Point3D extends Point {
    int z;
}

interface Colorable {
    void setColor(int color);
}

class ColoredPoint extends Point implements Colorable {
    int color;

    @Override
    public void setColor(int color) {
        this.color = color;
    }
}