package net.zhaoyu.javapros.j2se.expression;

/**
 * 字段的访问结构是根据其变量的类型决定的。
 */
public class FieldAccessTest {
    public static void main(String[] args) {
        T t = new T();
        System.out.println("t.x=" + t.x + when("t", t));
        S s = new S();
        System.out.println("s.x=" + s.x + when("s", s));

        /*
         输出：
         s.x=0, s 在运行时的类为 class net.zhaoyu.javapros.j2se.expression.T
         字段的访问不依赖于运行时引用对象的类，即使s指向了Class T的一个对象，表达式s.x依然指向Class S的x字段。
         因为s的表达式类型是S。T的对象包含了两个x的字段，一个是T的，另一个是父类S的。
        */
        s = t;
        System.out.println("s.x=" + s.x + when("s", s));

        Tm tm = new Tm();
        System.out.println("tm.x=" + tm.z() + when("tm", tm));
        Sm sm = new Sm();
        System.out.println("sm.x=" + sm.z() + when("sm", sm));

        /* 输出：
           sm.x=1, sm 在运行时的类为 class net.zhaoyu.javapros.j2se.expression.Tm
           和字段的访问比较，实例方法z()的访问是和运行时实例绑定的，因为T.z覆盖了S.z方法。
         */
        sm = tm;
        System.out.println("sm.x=" + sm.z() + when("sm", sm));
    }

    static String when(String name, Object t) {
        return ", " + name + " 在运行时的类为 " + t.getClass();
    }
}

class S {
    int x=0;
}

class T extends S{
    int x=1;
}

class Sm {
    int x=0;
    int z(){
        return x;
    }
}

class Tm extends Sm{
    int x=1;
    int z(){
        return x;
    }
}