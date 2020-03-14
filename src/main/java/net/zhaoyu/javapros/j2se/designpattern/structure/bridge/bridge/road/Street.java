package net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.road;


public class Street extends  AbstractRoad {
    @Override
    public void drive() {
        abstractPerson.drive();
        System.out.println("在街道上");
    }
}
