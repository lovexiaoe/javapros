package com.zhaoyu.designpattern.structure.bridge.bridge.car;

public class Bus extends  AbstractCar {
    @Override
    public void drive() {
        abstractRoad.drive();
        System.out.println("开公交车");
    }
}
