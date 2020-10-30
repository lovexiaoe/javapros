package net.zhaoyu.javapros.test;

/**
 * @Description: 计算0=20000之间的素数个数
 * @Author: zhaoyu
 * @Date: 2020/10/19
 */
public class Calculator implements Runnable {

    @Override
    public void run() {
        long current=1;
        long max = 20000L;
        long numPrimes=0L;

        System.out.printf("Thread '%s': START\n", Thread.currentThread().getName());
        while (current <= max) {
            if (isPrime(current)) {
                numPrimes++;
            }
            current++;
        }
        System.out.printf("Thread '%s': END. Number of Primes: %d\n", Thread.currentThread().getName(), numPrimes);
    }

    //计算是否是素数
    private boolean isPrime(long number){
        if (number <= 2) {
            return true;
        }
        for (int i = 2; i <number; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }
        return true;
    }
}
