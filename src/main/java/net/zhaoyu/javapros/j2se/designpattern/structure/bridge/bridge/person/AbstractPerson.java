package net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.person;

public abstract class AbstractPerson {
    //我们这里把“人”作为桥接的最后一个元素不引用其他元素，“车”和“路”作为中间元素。元素顺序都可以任意变换。
    public abstract void drive();
}
