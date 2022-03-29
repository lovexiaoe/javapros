package net.zhaoyu.javapros.j2se.expression;

import net.zhaoyu.javapros.j2se.threads.CallableFuture.runnablefuturetask.Task;

import java.util.Comparator;

/**
 * 方法引用是lambda表达式的一种写法，可以让我们将方法当做值来传递。方法引用求值不会引起对应方法的执行。
 * 如下是一些方法引用的表达式。
 * String::length // 实例方法
 * System::currentTimeMillis // 静态方法
 * List<String>::size // 泛型的类型引元
 * List::size // 不使用泛型的类型引元
 * int[]::clone //数组clone
 * T::tvarMember
 *
 * System.out::println
 * "abc"::length //特定对象的方法引用
 * foo[x]::bar //数组元素调用
 * (test ? list.replaceAll(String::trim) : list) :: iterator //调用iterator
 * super::toString //super调用
 *
 * Arrays::sort // 类型引元需要从上下文推断。
 * Arrays::<String>sort // 声明类型引元
 *
 * 下面的表达式表示一个对象或者数组的延迟创建。
 * ArrayList<String>::new // 带参数化类型的构造方法
 * ArrayList::new // 无类型引元的构造方法，类型引元需要推断
 * Foo::<Integer>new // 显示声明构造方法的类型引元
 * Bar<String>::<Integer>new // 声明泛型类和泛型构造器
 * Outer.Inner::new // 内部类构造方法
 * int[]::new // 创建数组
 *
 *
 */
public class MethodReferenceTest {

}
interface Fun<T,R> { R apply(T arg); }

class C {
    int size() { return 0; }
    int size(Object arg) { return 0; }
    int size(C arg) { return 0; }
    void test() {
        // OK: reference is to instance method size()
        Fun<C, Integer> f1 = C::size;
    }
}

class C1 {
    int size() { return 0; }
    static int size(Object arg) { return 0; }
    int size(C1 arg) { return 0; }
    void test() {
        // Error: 表达式C1::size匹配实例方法 size() 和静态方法 size(Object)。所以会报错。
//        Fun<C1, Integer> f1 = C1::size;
    }
}

/*
一个lambda表达式兼容一个函数类型（functional interface）。当如下条件被满足时。
1. 表达式的参数和函数类型的参数一致。
2. 函数类型为void，lambda是语句表达式或者void语句块。
3. 函数类型有一个返回值类型，lambda是和返回值类型兼容的表达式或者语句块。

一个方法引用表达式兼容一个函数类型（functional interface）。如果函数类型的参数为n，则对于方法引用表达式，
至少存在如下一种兼容的方法。
1. 方法引用表达式的格式是`ReferenceType :: [TypeArguments] Identifier`至少一个兼容的方法是
    1. 方法为static且支持参数n。
    2. 方法为非static且支持参数n-1。
2. 方法引用表达式是其他格式，且至少一个兼容的方法是非static。
*/
