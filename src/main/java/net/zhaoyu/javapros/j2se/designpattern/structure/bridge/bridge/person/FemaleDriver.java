package net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.person;

public class FemaleDriver extends  AbstractPerson {
    @Override
    public void drive() {
        System.out.println("女司机");
    }
}
