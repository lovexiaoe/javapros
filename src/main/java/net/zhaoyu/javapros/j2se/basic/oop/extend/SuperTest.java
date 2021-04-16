package net.zhaoyu.javapros.j2se.basic.oop.extend;

/**
 * 子类的构造方法必须在第一行加上super。如果不加，java会默认加上父类的无参构造方法super()。
 * 如果父类没有无参构造方法，那么会报错。
 */
public class SuperTest {
}
class Father{
    public String name;

    public Father(String name) {
        this.name = name;
    }
}
class Son extends  Father{
    //这里必须写super，因为Father没有默认的无参构造方法。
    public Son(String name) {
        super(name);
    }
}