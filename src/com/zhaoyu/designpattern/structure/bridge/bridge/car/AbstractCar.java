package com.zhaoyu.designpattern.structure.bridge.bridge.car;

import com.zhaoyu.designpattern.structure.bridge.bridge.road.AbstractRoad;

public abstract class AbstractCar {
    protected AbstractRoad abstractRoad;
    public void setAbstractRoad(AbstractRoad abstractRoad){
        this.abstractRoad=abstractRoad;
    }
    public abstract  void drive();
}
