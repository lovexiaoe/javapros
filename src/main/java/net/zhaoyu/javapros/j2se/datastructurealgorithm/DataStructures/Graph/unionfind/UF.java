package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.unionfind;

/**
 * unionfind并查集接口
 */
public interface UF {
    int getSize();
    void union(int p, int q);
    boolean isConnected(int p, int q);
}
