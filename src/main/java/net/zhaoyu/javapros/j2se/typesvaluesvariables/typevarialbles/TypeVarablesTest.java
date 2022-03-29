package net.zhaoyu.javapros.j2se.typesvaluesvariables.typevarialbles;

/**
 * 如果一个类声明了一个或者多个类型变量（type variables），则被称为泛型。
 * 类型变量也被称为类的类型参数（type parameters）。
 *
 * 类型变量是一种非限定标识符，在类、接口方法、构造方法体种被作为一种类型使用。
 *
 * 每个类型变量作为类型参数都有一个边界（bound），没有声明则默认为Object。
 * 如下的T就是一个类型变量。它的边界为C&I
 * 类型变量的例子如下：T, <S extends String>
 */
public class TypeVarablesTest {
    <T extends C&I> void test(T t){
        t.mI();
        t.mCPackage();
        t.mCPublic();
        t.mCProtected();
//        t.mCPrivate(); //编译错误，private方法无权限。
    }
}
class C{
    public void mCPublic(){}
    protected void mCProtected(){}
    void mCPackage(){}
    private void mCPrivate(){}
}

interface I {
    void mI();
}

class CT extends C implements I{
    public void mI() {}
}