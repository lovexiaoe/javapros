package com.mianshi.exercise;

import java.math.BigInteger;
import java.util.Random;

public class WinPrize {

    //抽奖的算法，根据权重算出抽奖。有问题，但是可以用来参考。
    //标准的权重算法是将多维权重总和相乘，然后看随机数落到矩阵的那个地方（累减计算具体落点）。
    public static void testWinPrize() {
        int[] prizeWeight = new int[]{20, 20, 40, 80, 9840};
        int[] ordinalGroup = new int[]{1, 2, 5, 12};
        int[] orderAmountIndex = new int[]{10, 20, 70};

        System.out.println("10000次排序权重为1，订单权重为70");
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;
        int prize;
        for (int i = 0; i < 10000; i++) {
            prize = winPrize(prizeWeight, ordinalGroup, orderAmountIndex, 0, 2);
            if (prize < 0) {
                System.out.println("错误" + prize);
            } else if (0 == prize) {
                count1++;
            } else if (1 == prize) {
                count2++;
            } else if (2 == prize) {
                count3++;
            } else if (3 == prize) {
                count4++;
            } else if (4 == prize) {
                count5++;
            } else {
                System.out.println("未知的返回值" + prize);
            }
        }
        System.out.println("一等奖中奖数" + count1);
        System.out.println("二等奖中奖数" + count2);
        System.out.println("三等奖中奖数" + count3);
        System.out.println("四等奖中奖数" + count4);
        System.out.println("未中奖数" + count5);
    }

    /**
     * 根据奖品和属性权重计算随机中奖的奖品。
     * 该函数处理各个权重总和的乘积小于int的最大值2147483647的情况。
     *
     * @param prizeWeight      奖品权重，如：一等奖：1，二等奖：2，三等奖：4，不中奖80。参数为[1,2,4,80]
     * @param ordinalGroup     排序分组权重【1-700】笔1个，【700-1000】笔1个，...，传入[1,1,...]
     * @param orderAmountGroup 订单金额权重【1-100】元1个，【100-200】元1个，...，传入[1,1,...]
     * @param ordinalIndex     排序分组命中下标。
     * @param orderAmountIndex 订单金额权重命中下标。
     * @return >-1
     * -2 prizeWeight参数不能为空
     * -3 ordinalGroup参数不能为空。
     * -4 orderAmountGroup参数不能为空
     * -5 权重总数相乘大于int最大值
     * -1 计算程序错误。
     */
    public static int winPrize(int[] prizeWeight, int[] ordinalGroup, int[] orderAmountGroup, int ordinalIndex, int orderAmountIndex) {
        int result = -1;
        int prizeLength = prizeWeight.length;
        int ordinalLength = ordinalGroup.length;
        int orderLength = orderAmountGroup.length;
        if (prizeLength < 0) {
            return -2;
        }
        if (ordinalLength < 0) {
            return -3;
        }
        if (orderLength < 0) {
            return -4;
        }
        BigInteger random_max = BigInteger.ZERO;
        BigInteger ordinalSum = BigInteger.ZERO;
        BigInteger orderSum = BigInteger.ZERO;
        int[] finalPrizeWeight = new int[prizeLength];
        for (int i = 0; i < prizeLength; i++) {
            random_max = random_max.add(BigInteger.valueOf(prizeWeight[i]));
            finalPrizeWeight[i] = prizeWeight[i] * ordinalGroup[ordinalIndex] * orderAmountGroup[orderAmountIndex];
        }
        for (int i = 0; i < ordinalLength; i++) {
            ordinalSum = ordinalSum.add(BigInteger.valueOf(ordinalGroup[i]));
        }
        for (int i = 0; i < orderLength; i++) {
            orderSum = orderSum.add(BigInteger.valueOf(orderAmountGroup[i]));
        }
        random_max = random_max.multiply(ordinalSum).multiply(orderSum);
        if (random_max.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            return -5;
        }

        Random random = new Random();
        int r = random.nextInt(random_max.intValue());

        for (int i = 0; i < finalPrizeWeight.length; i++) {
            if (r < finalPrizeWeight[i]) {
                result = i;
                break;
            }
            r -= finalPrizeWeight[i];
        }
        if (result == -1) {
            System.out.println("...");
        }
        return result;
    }


}
