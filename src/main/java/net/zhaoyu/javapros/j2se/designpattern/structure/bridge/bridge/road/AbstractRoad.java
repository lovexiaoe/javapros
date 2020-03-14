package net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.road;

import com.zhaoyu.designpattern.structure.bridge.bridge.person.AbstractPerson;

public abstract class AbstractRoad {
    protected AbstractPerson abstractPerson;
    public void setAbstractPerson(AbstractPerson abstractPerson){
        this.abstractPerson=abstractPerson;
    }
    public abstract void drive();
}
