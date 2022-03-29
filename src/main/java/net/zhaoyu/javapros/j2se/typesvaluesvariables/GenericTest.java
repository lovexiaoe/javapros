package net.zhaoyu.javapros.j2se.typesvaluesvariables;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {
    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<>();
        Box<String> stringBox = new Box<>();
        integerBox.add(10);
        stringBox.add("abc");
//        stringBox.add(11); //编译错误。
        System.out.println("整数box："+integerBox.get());
        System.out.println("字符串box："+stringBox.get());

        List l = new ArrayList<Number>();
        l.add(1);
        List<String> ls=l;
        System.out.println(ls);
    }
}

class Box<T> {
    T t;
    public void add(T t) {
        this.t=t;
    }
    public T get(){
        return t;
    }
}
