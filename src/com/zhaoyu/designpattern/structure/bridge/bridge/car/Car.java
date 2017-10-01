package com.zhaoyu.designpattern.structure.bridge.bridge.car;

public class Car extends  AbstractCar {
    @Override
    public void drive() {
        abstractRoad.drive();
        System.out.println("开小汽车");
    }
}
