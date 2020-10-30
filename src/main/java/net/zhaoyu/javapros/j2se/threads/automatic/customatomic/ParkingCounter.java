package net.zhaoyu.javapros.j2se.threads.automatic.customatomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 模拟一个停车场计数器，最多能停maxNumber辆车
 * @Author: zhaoyu
 * @Date: 2020/10/24
 */
public class ParkingCounter extends AtomicInteger {
    private final int maxNumber;

    public ParkingCounter(int maxNumber) {
        set(0);
        this.maxNumber = maxNumber;
    }

    /**
     * 车辆进入，自旋，使用cas。
     * @return
     */
    public boolean carIn(){
        for (; ; ) {
            int value=get();
            if (value == maxNumber) {
                System.out.println("停车计数器：停车场已满。");
                return false;
            } else {
                int newValue=value+1;
                boolean changed = compareAndSet(value, newValue);
                if (changed) {
                    System.out.println("停车计数器：有一辆车进入");
                    return true;
                }
            }
        }
    }

    /**
     * 车辆出库，自旋，使用cas。
     * @return
     */
    public boolean carOut(){
        for (; ; ) {
            int value=get();
            if (value == 0) {
                System.out.println("停车计数器：停车场已空");
                return false;
            } else {
                int newValue=value-1;
                boolean changed = compareAndSet(value, newValue);
                if (changed) {
                    System.out.println("停车计数器：有一辆车离开");
                    return true;
                }
            }
        }
    }
}
