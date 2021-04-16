package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.HashMap;

import java.util.Arrays;

/**
 * HashMap线性扩展，数据结构为一个数组，当hash冲突时，会顺延到下一个没有占用的数组元素中。查找也对应从hash的位置开始，如果没有，顺延向后查。
 * 当负载大于.7时，使用动态数据进行空间扩展。
 * @Author: zhaoyu
 * @Date: 2020/12/28
 */
public class HashMapLinearProbing {
    /** hash表的长度 */
    private int hsize;
    /** 代表hash表的数组 */
    private Integer[] buckets;
    private Integer AVAILABLE;
    /** hash表中元素的数量 */
    private int size;

    public HashMapLinearProbing(int hsize) {
        this.buckets = new Integer[hsize];
        this.hsize = hsize;
        this.AVAILABLE = Integer.MIN_VALUE;
        this.size = 0;
    }

    /**
     * 简单的hash算法
     * @param key
     * @return int
     */
    public int hashing(int key) {
        int hash = key % hsize;
        if (hash < 0) {
            hash += hsize;
        }
        return hash;
    }

    public void insertHash(int key){
        int hash=hashing(key);
        if (isFull()) {
            System.out.println("hash表已满");
            return;
        }

        for (int i = 0; i < hsize; i++) {
            if (buckets[hash] == null || buckets[hash].equals(AVAILABLE)) {
                buckets[hash]=key;
                size++;
                return;
            }

            if (hash + 1 < hsize) {
                hash++;
            } else {
                hash=0;
            }
        }
    }

    public void deleteHash(int key){
        int hash = hashing(key);

        if (isEmpty()) {
            System.out.println("hash表为空");
            return;
        }

        for (int i = 0; i < hsize; i++) {
            if (buckets[hash] != null && buckets[hash].equals(key)) {
                buckets[hash] = AVAILABLE;
                size--;
                return;
            }
            if (hash + 1 < hsize) {
                hash++;
            } else {
                hash=0;
            }
        }
        System.out.println("未找到key="+key);
    }

    /**
     * 打印hash表
     * @param
     * @return void
     */
    public void displayHashtable() {
        for (int i = 0; i < hsize; i++) {
            if (buckets[i] == null || buckets[i] == AVAILABLE) {
                System.out.println("Bucket " + i + ": Empty");
            } else {
                System.out.println("Bucket " + i + ": " + buckets[i].toString());
            }
        }
    }

    /**
     * 查找输入key的index
     *
     * @param key
     * @return
     */
    public int findHash(int key) {
        int hash = hashing(key);

        if (isEmpty()) {
            System.out.println("Table is empty");
            return -1;
        }

        for (int i = 0; i < hsize; i++) {
            try {
                if (buckets[hash].equals(key)) {
                    buckets[hash] = AVAILABLE;
                    return hash;
                }
            } catch (Exception E) {
            }

            if (hash + 1 < hsize) {
                hash++;
            } else {
                hash = 0;
            }
        }
        System.out.println("未找到Key=" + key);
        return -1;
    }

    /**
     * 2倍扩展hash表
     * @param
     * @return void
     */
    private void lengthenTable() {
        buckets = Arrays.copyOf(buckets, hsize * 2);
        hsize *= 2;
        System.out.println("Table size is now: " + hsize);
    }

    /**
     * 如果负载大于.7，自动扩展hash表。
     * @param
     * @return void
     */
    public void checkLoadFactor() {
        double factor = (double) size / hsize;
        if (factor > .7) {
            System.out.println("Load factor is " + factor + ",  lengthening table");
            lengthenTable();
        } else {
            System.out.println("Load factor is " + factor);
        }
    }

    private boolean isEmpty() {
        boolean response=true;
        for (int i = 0; i < hsize; i++) {
            if (buckets[i] != null) {
                response=false;
                break;
            }
        }
        return response;
    }

    public boolean isFull(){
        boolean response=true;
        for (int i = 0; i < hsize; i++) {
            if (buckets[i]==null|| buckets[i].equals(AVAILABLE)) {
                response = false;
                break;
            }
        }
        return response;
    }
}
