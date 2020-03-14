package net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.car;

public class Bus extends  AbstractCar {
    @Override
    public void drive() {
        abstractRoad.drive();
        System.out.println("开公交车");
    }
}
