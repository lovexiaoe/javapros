package net.zhaoyu.javapros.j2se.datastructurealgorithm.algorithm.search;

/**
 *
 */
public class BianrySearch {
    public static void main(String[] args) {

    }
    /**
     * 下面的方法在一个升序排列的array中二分查找v
     * @param array 升序的有序数组
     * @param v 要查找的目标值
     * @return 目标值在数组中的位置。
     */
    public int bsearch(int[] array, int v) {
        int left = 0, right = array.length - 1, middle;
        while (left <= right) { //left和right可以重合。
            middle = left + (right - left) / 2; //middle的计算。
            if (array[middle] > v) {
                right = middle - 1;  //排除已比对的middle位置
            } else if (array[middle] < v) {
                left = middle + 1;  //排除已比对的middle位置
            } else {
                return middle;
            }
        }
        return -1;
    }
}
