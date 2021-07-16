package net.zhaoyu.javapros.test;


import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap concurMap = new ConcurrentHashMap(10);

        concurMap.put("k1", "v1");

        System.out.println("success");

    }


}
