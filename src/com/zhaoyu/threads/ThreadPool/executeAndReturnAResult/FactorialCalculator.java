package com.zhaoyu.threads.ThreadPool.executeAndReturnAResult;

import java.util.concurrent.Callable;

/**
 * 计算一个数的阶乘数。如3 =1*2*3=6
 */
public class FactorialCalculator implements Callable<Integer> {

    private final Integer number;

    public FactorialCalculator(Integer number){
        this.number=number;
    }

    @Override
    public Integer call() throws Exception {
        int num, result;

        num=number.intValue();
        result=1;

        // If the number is 0 or 1, return the 1 value
        if ((num==0)||(num==1)) {
            result=1;
        } else {
            // Else, calculate the factorial
            for (int i=2; i<=number; i++) {
                result*=i;
                Thread.sleep(20);
            }
        }
        System.out.printf("%s: %d\n",Thread.currentThread().getName(),result);
        // Return the value
        return result;
    }
}