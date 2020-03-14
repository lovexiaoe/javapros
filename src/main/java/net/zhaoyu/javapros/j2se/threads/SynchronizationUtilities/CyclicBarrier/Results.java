package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CyclicBarrier;

/**
 * 存放一个数字在矩阵中每一行出现的次数。
 */
public class Results {

    private final int data[];

    public Results(int size){
        data=new int[size];
    }

    public void  setData(int position, int value){
        data[position]=value;
    }

    public int[] getData(){
        return data;
    }
}
