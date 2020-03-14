package net.zhaoyu.javapros.j2se.utils.time;

import java.time.Clock;

/**
 * 输出两小时后的时间戳。
 */
public class TimeTest {
    public static void main(String[] args) {
        System.out.println(Clock.systemUTC().millis() / 1000 + 60 * 2);
    }
}
