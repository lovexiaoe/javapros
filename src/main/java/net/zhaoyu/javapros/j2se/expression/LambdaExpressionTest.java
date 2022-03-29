package net.zhaoyu.javapros.j2se.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * lambda表达式就像一个方法，提供了一个参数列表和一个代码体，格式如下。
 * LambdaExpression:
 *      LambdaParameters -> LambdaBody
 *
 * lambda例子如下：
 * () -> {}
 * () -> 42
 * () -> { return 42; }
 * () -> {  //代码块表示
 *  if (true)
 *      return 12;
 *  else {
 *      return 15;
 *  }
 * }
 *
 * //单参数
 * (int x) -> x+1
 * (int x) -> { return x+1; }
 * (x) -> x+1
 * x -> x+1
 *
 * //多参数
 * (int x, int y) -> x+y
 * (x, y) -> x+y
 *
 * 在lambda表达式体中使用到的任何本地变量、形参、exception参数都必须被final修饰或者实际上是final的。否则会编译错误
 * 在lambda表达式体中使用到的任何本地变量必须被确定赋值，否则会编译错误。
 *
 * 在运行时，lambda表达式的执行和类实例创建表达式的执行类似，在完成的时候会生成对对象的引用。
 */
public class LambdaExpressionTest {
    void m1(int x) {
        int y = 1;
        foo(() -> x+y); // x和y必须是实际上final的。
    }
    void m3(int x) {
        int y;
        if (x==2) y = 1;
        // 错误: y是本地变量，y 实际上是 final, 有if判断，所以y不是确定被赋值的.
//        foo(() -> x+y);
    }
    void m4(int x) {
        int y;
        if (x==2) y = 1; else y = 2;
        // 正确:  y 被最终确定赋值.
        foo(() -> x+y);
    }
    void m5(int x) {
        int y;
        if (x==2) y = 1;
        y = 2;
        // 错误: y 被赋值两次，不是实际上final的。
//        foo(() -> x+y);
    }
    void m6(int x) {
        // 错误: x 不是实际上final的.
//        foo(() -> x+1);
        x++;
    }
    void m7(int x) {
        // 错误: x 不是实际 final.
//        foo(() -> x=1);
    }
    void m8() {
        int y;
        // 错误: y 在 lambda之前，没有被确定赋值。
//        foo(() -> y=1);
    }
    void m9(String[] arr) {
        for (String s : arr) {
            // 正确: s 是每次循环的新变量，所以是final的
            foo(() -> s);
        }
    }
    void m10(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 错误: i 不是final的。
//            foo(() -> arr[i]);
        }
    }


    void foo(Supplier supplier){

    }

    public static void main(String[] args) {
        List list=new ArrayList();
        // 如果lambda表达式体是一个语句表达式，那么它兼容返回void的函数类型，如下，list.add也兼容Consumer。
        // 当lambda表达式“()->expr”，其中expr是语句时，会基于目标被翻译为“()->{return expr;}”或“()->{expr;}”
        Predicate<String> p = s -> list.add(s);
        Consumer<String> c=s->list.add(s);
    }
}
