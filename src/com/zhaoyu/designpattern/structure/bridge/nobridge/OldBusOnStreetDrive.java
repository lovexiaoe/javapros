package com.zhaoyu.designpattern.structure.bridge.nobridge;

//这里实现老司机在街道上开公交车，结构太复杂了，需要继承太多层。还有女司机在高速路上开小汽车等等。

public class OldBusOnStreetDrive extends BusOnStreetDrive{
    public void dirve(){
        System.out.println("老司机在街道上开车,开的公交车");
    }

    public static void main(String[] args){
        //老司机在街道上开公交车
        OldBusOnStreetDrive oldBusOnStreetDrive=new OldBusOnStreetDrive();
        oldBusOnStreetDrive.dirve();

        //女司机在高速路上开小汽车等等,......s
    }
}

