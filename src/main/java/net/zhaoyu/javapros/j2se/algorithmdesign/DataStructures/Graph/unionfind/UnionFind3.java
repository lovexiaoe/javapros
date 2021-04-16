package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph.unionfind;

public class UnionFind3 implements UF{
    private int[] parent;
    private int[] rank; //记录树的高度，以降低树的高度，也可以使用AVL等方案代替。
    @Override
    public int getSize() {
        return parent.length;
    }

    /**
     * 初始化数组，让每一个节点都是根节点。也就是值和id相等。
     * @param size
     */
    public UnionFind3(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 1; //初始化高度为1
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
     * 连接两个节点，使用rank降低树的高度。
     * @param p
     * @param q
     */
    @Override
    public void union(int p, int q) {
        int pRoot = findParent(p);
        int qRoot = findParent(q);

        if (isConnected(p, q))
            return;
        // 高度不同的连接不会增加树的高度
        if (rank[pRoot] < rank[qRoot])
            parent[pRoot] = qRoot;
        else if (rank[pRoot] > rank[qRoot])
            parent[qRoot] = pRoot;
        else{
            parent[qRoot] = pRoot;
            rank[pRoot]++; //相同高度增加1
        }
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
