package net.zhaoyu.javapros.j2se.basic.interfaces.annotation;

/**
 * 预定义注解
 * @Target 定义注解执行的元素，包括{@link java.lang.annotation.ElementType}
 * @Retention 定义注解保留的时间，包括{@link java.lang.annotation.RetentionPolicy}
 * @Inherited 定义是否从父类继承该注解。父类定义了某个Inherited修饰的注解，则子类也生效。
 * @Override 定义方法重写。
 * @SuppressWarnings java编译器，提示的警告越来越多，有些不需要的警告，可以屏蔽。可以在value变量中声明具体屏蔽的警告，如unchecked。
 * @SafeVarargs 在声明有模糊类型的可变参数的构造函数或方法时，java编译期会报unchecked警告。如果程序显示声明了不会对varargs参数执行
 *      不安全的操作，可以使用该注解标记，编译器就不会报unchecked警告了。只适用static或final方法。
 */
public class PreDefinedAnnotations {
}
