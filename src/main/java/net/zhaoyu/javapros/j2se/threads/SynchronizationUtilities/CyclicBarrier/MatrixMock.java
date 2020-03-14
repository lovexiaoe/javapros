package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CyclicBarrier;

import java.util.Random;

/**
 * Matrix-矩阵
 *
 * 该类生成一个从1到10整数的随机矩阵。
 */
public class MatrixMock {
    /**
     * Bi-dimensional array with the random numbers
     */
    private final int data[][];

    /**
     * 生成一个二维数组
     * 计算我们需要查找的数字出现了几次
     * @param size 数组的行数
     * @param length 数组的列数
     * @param number 我们需要查找的数字。
     */
    public MatrixMock(int size, int length, int number){

        int counter=0;
        data=new int[size][length];
        Random random=new Random();
        for (int i=0; i<size; i++) {
            for (int j=0; j<length; j++){
                data[i][j]=random.nextInt(10);
                if (data[i][j]==number){
                    counter++;
                }
            }
        }
        System.out.printf("矩阵查找: 数字 %d 在生成的矩阵中有 %d 次出现.\n",number,counter);
    }

    /**
     * 返回 bi-dimensional array 的一行
     */
    public int[] getRow(int row){
        if ((row>=0)&&(row<data.length)){
            return data[row];
        }
        return null;
    }
}
