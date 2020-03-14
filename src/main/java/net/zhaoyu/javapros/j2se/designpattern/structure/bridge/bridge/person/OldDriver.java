package net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.person;

public class OldDriver extends  AbstractPerson {
    @Override
    public void drive() {
        System.out.println("老司机");
    }
}
