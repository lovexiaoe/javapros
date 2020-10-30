package net.zhaoyu.javapros.j2se.threads.automatic.customatomic;

/**
 * @Description:
 * @Author: zhaoyu
 * @Date: 2020/10/24
 */
public class Sensor2 implements Runnable {

    private final ParkingCounter counter;

    public Sensor2(ParkingCounter parkingCounter) {
        this.counter = parkingCounter;
    }

    @Override
    public void run() {
        counter.carIn();
        counter.carOut();
        counter.carOut();
        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();
    }
}
