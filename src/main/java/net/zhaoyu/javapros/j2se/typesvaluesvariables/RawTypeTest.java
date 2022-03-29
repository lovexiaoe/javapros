package net.zhaoyu.javapros.j2se.typesvaluesvariables;

/**
 * 为了方便和非泛型的遗留代码交互，定义了RawType。rawType是针对于泛型来讲的。
 * RawType的情况有下面几种：
 * 1.声明泛型的名称形成的不带参数列表的名称，如：Comparator<T>的RawType为Comparator。
 * 2.元素类型为raw type的数组类型，如Integer[]。
 * 3.某个raw type类型R的非静态成员类型，且该类型没有R的父接口或者父类。
 *
 */
public class RawTypeTest {
}

/**
 * 如下Inner依赖于Outer，且Outer是raw，Inner也没有和T绑定，则可以认为Inner是raw。符合第三种情况。
 */
class Outer<T> {
    T t;
    class Inner {
        T setOuterT(T t1) {
            t=t1;
            return t;
        }
    }
}
