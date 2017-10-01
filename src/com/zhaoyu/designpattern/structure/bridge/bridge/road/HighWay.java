package com.zhaoyu.designpattern.structure.bridge.bridge.road;

import com.zhaoyu.designpattern.structure.bridge.bridge.person.AbstractPerson;

public class HighWay extends  AbstractRoad {
    @Override
    public void drive() {
        abstractPerson.drive();
        System.out.println("在高速公路上");
    }
}
