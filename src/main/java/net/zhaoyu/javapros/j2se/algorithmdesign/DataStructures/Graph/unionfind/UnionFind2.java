package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph.unionfind;

public class UnionFind2 implements UF{
    private int[] parent;
    @Override
    public int getSize() {
        return parent.length;
    }

    /**
     * 初始化数组，让每一个节点都是根节点。也就是值和id相等。
     * @param size
     */
    public UnionFind2(int size) {
        parent = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i]=i;
        }
    }

    /**
     * 查找根节点
     * @param id
     * @return
     */
    private int findParent(int id) {
        if (id < 0 || id >= parent.length)
            throw new IllegalArgumentException("id 越界");
        while (parent[id] != id) { //结束条件id和值相同，即为根节点
            id = parent[id];
        }
        return id;
    }

    /**
     * 连接两个节点，我们不是直接连接修改数组数值，而是规定让某个树的根节点去连接另一个树的根节点。参考图unionfind2-union.png
     * @param p
     * @param q
     */
    @Override
    public void union(int p, int q) {
        if (isConnected(p, q))
            return;
        int parent_p = findParent(p);
        parent[parent_p] = findParent(q);
    }

    /**
     * p和q节点是否连接，查找根节点是否相同，根节点相同，说明是在同一个集合中，他们是互相连接的
     * @param p
     * @param q
     * @return
     */
    @Override
    public boolean isConnected(int p, int q) {
        return findParent(p)==findParent(q);
    }
}
