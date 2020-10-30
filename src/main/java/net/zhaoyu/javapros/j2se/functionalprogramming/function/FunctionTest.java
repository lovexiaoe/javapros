package net.zhaoyu.javapros.j2se.functionalprogramming.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Function接口的使用，可以作为参数传递。使用lambda表达式实现。
 */
public class FunctionTest {
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T t: list) {
            result.add(f.apply(t));
        }
        return result;
    }

    public static void main(String[] args) {
        //(String s) -> s.length()  lambda表达式实现Function。
        List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
        // [7, 2, 6]
        l.forEach(len->System.out.println(len));
    }
}
