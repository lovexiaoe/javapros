package net.zhaoyu.javapros.j2se.expression;

/**
 * 使用super访问字段。
 */
interface I { int x = 0; }
class T1 implements I { int x = 1; }
class T2 extends T1 { int x = 2; }
class T3 extends T2 {
    int x = 3;
    void test() {
        System.out.println("x=\t\t" + x);
        System.out.println("super.x=\t\t" + super.x);
        System.out.println("((T2)this).x=\t" + ((T2)this).x);
        System.out.println("((T1)this).x=\t" + ((T1)this).x);
        System.out.println("((I)this).x=\t" + ((I)this).x);
    }
}
public class SuperFieldAccessTest extends T2 {
    public static void main(String[] args) {
        new T3().test();
    }
}
/* 输出：
x=		3
super.x=		2
((T2)this).x=	2
((T1)this).x=	1
((I)this).x=	0
 */