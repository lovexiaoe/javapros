package net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge;


import net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.car.AbstractCar;
import net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.car.Bus;
import net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.person.AbstractPerson;
import net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.person.OldDriver;
import net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.road.AbstractRoad;
import net.zhaoyu.javapros.j2se.designpattern.structure.bridge.bridge.road.HighWay;

public class Client {
    public static void main(String[] args) {
        //在这里讲人，路，车三要素桥接起来，每个要素将抽象和实现分离，抽象部分可以用抽象类，也可以用接口。
        //老司机在高速上开公交车
        AbstractCar abstractCar1=new Bus();
        AbstractRoad abstractRoad1=new HighWay();
        AbstractPerson oldDriver= new OldDriver();
        abstractRoad1.setAbstractPerson(oldDriver);
        abstractCar1.setAbstractRoad(abstractRoad1);
        abstractCar1.drive();
    }
}
