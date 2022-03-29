package net.zhaoyu.javapros.j2se.datastructurealgorithm.algorithm.search;

import java.util.Arrays;

public class KMP {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getNext("AAAB")));
        System.out.println(Arrays.toString(getNext("ababc")));
        System.out.println(Arrays.toString(getNext("aaabc")));
        String s = "abcabdef";
        String p = "abd";

        System.out.println("kmpSearch: "+p+" from "+s+" = "+kmpMatch(s,p));
    }

    /**
     * 获取KMP算法的next数组
     * @param p
     * @return
     */
    public static int[] getNext(String p){
        char[] pc = p.toCharArray();

        int[] next = new int[p.length()];
        int j = 0; //字符串下标。
        int k = -1; //前后缀计数。

        next[0]=-1; //首位默认设置为-1，方便计算。

        while (j < pc.length - 1) {
            if (k == -1 || pc[j] == pc[k]) { //如果是首次比对或比对当前元素相同，则继续向后比对。
                next[++j] = ++k;
            } else { //如果比对当前元素不相等，那么找到和前缀相同的位置再进行匹配。
                k = next[k]; //回缩前缀计数。
            }
        }

        return next;
    }


    /**
     * KMP查找实现
     * @param s 查找串
     * @param p 目标串
     * @return 目标串在查找串中的位置
     */
    public static int kmpMatch(String s,String p){
        char[] sc=s.toCharArray();
        char[] pc=p.toCharArray();

        int[] next = getNext(p);
        int i=0,j=0;

        while (i < sc.length&&j<pc.length) {
            if (j==-1||sc[i] == pc[j]) {
                ++i;
                ++j;
            } else {
                j = next[j];
            }
        }
        if (j == pc.length) {
            return i - j;
        } else {
            return -1;
        }
    }

}
