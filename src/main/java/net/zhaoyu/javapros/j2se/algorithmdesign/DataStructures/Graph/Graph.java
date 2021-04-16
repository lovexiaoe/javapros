package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph;

import zhaoyu.DataStructures.Lists.LinkedList;
import zhaoyu.DataStructures.Trees.BST.BinarySearchTree;
import zhaoyu.DataStructures.Trees.BST.BinaryTree;
import zhaoyu.DataStructures.Trees.RedBlack.RedBlackTree;

import java.util.PriorityQueue;

public interface Graph <V, E>{
    /**
     * 添加顶点，返回节点id
     * @return
     */
    int addVertex();

    /**
     * 删除顶点
     * @param id
     */
    void removeVertex(int id);

    /**
     * 添加边
     * @param source
     * @param target
     */
    void addEdge(int source, int target);

    /**
     * 删除边
     * @param source
     * @param target
     */
    void removeEdge(int source,int target);

    /**
     * 两个顶点是否相邻
     * @param source
     * @param target
     * @return
     */
    boolean isAdjacent(int source, int target);

    /**
     * 获取相邻顶点列表
     * @param source
     * @return
     */
    LinkedList<Integer> getNeighbors(int source);

    /**
     * 设置顶点的值
     * @param vertex
     * @param value
     */
    void setVertexValue(int vertex, V value);

    /**
     * 返回顶点的值
     * @param vertex
     * @return
     */
    V getVertexValue(int vertex);

    /**
     * 设置边的值
     * @param source
     * @param target
     * @param value
     */
    void setEdgeValue(int source, int target, E value);

    /**
     * 返回边的值
     * @param source
     * @param target
     * @return
     */
    E getEdgeValue(int source, int target);

    /**
     * 是否是无向图
     * @return
     */
    boolean isUndirected();

    /**
     * 获取所有的顶点
     * @return
     */
    BinarySearchTree<Integer> getAllVertices();

    /**
     * 返回所有的顶点最大ID
     * @return
     */
    int maxVertexID();

    enum TraversalType {
        DFT,BFT
    }

    /**
     * 遍历所有的顶点，类似于树的遍历，使用toBeProcessed记录待遍历的节点，使用状态数组doneProcessing记录了节点的处理状态。
     * 待处理列表中可能因为邻居的关系重复放入相同的未处理节点。
     * @param startingNode
     * @param visitor
     * @param type
     */
    default void visitAllConnectedVertices(int startingNode, TwoArgumentStatement<Integer,  V> visitor,
                                           TraversalType type){
        OrderedStore<Integer> toBeProcessed=null;
        boolean doneProcessing[] = new boolean[maxVertexID() + 1];
        switch (type) {
            case BFT:
                toBeProcessed = new QueueImplLinkedList<>();
                break;
            case DFT:
                toBeProcessed = new StackImplLinkedList<>();
                break;
        }
        toBeProcessed.insert(startingNode);
        while (toBeProcessed.checkFirst() != null) {
            int currentVertex = toBeProcessed.pickFirst();

            //因为邻居的原因可能会存放重复的未处理节点，如[BFGB],在后面的B节点处理后，前面的B节点应该被忽略。
            if (doneProcessing[currentVertex]) {
                continue;
            }
            doneProcessing[currentVertex]=true;
            visitor.doSomething(currentVertex,getVertexValue(currentVertex));

            //邻居中已经处理的节点，不会再次放入待处理列表中。
            for (int neighbor : getNeighbors(currentVertex)) {
                if (doneProcessing[neighbor] == false) {
                    toBeProcessed.insert(neighbor);
                }
            }
        }
    }

    /**
     * 遍历边时使用的对象
     */
    class Edge implements Comparable<Edge>{
        int source;
        int target;

        public Edge(int source, int target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Edge edge = (Edge) o;

            if (source != edge.source) return false;
            return target == edge.target;

        }

        @Override
        public int hashCode() {
            int result = source;
            result = 31 * result + target;
            return result;
        }


        @Override
        public int compareTo(Edge o) {
            if(source!=o.source){
                return source - o.source;
            }else {
                return target - o.target;
            }
        }
    }

    /**
     * 边的遍历，和顶点的遍历类似，根据边的终点为依据。
     * @param startingNode
     * @param visitor
     * @param type
     */
    default void visitAllConnectedEdges(int startingNode,ThreeArgumentStatement<Integer,Integer,E> visitor,
                                        TraversalType type){
        OrderedStore<Edge> toBeProcessed = null;
        boolean doneProcessing[] = new boolean[maxVertexID()+1];
        switch (type){
            case BFT: toBeProcessed = new QueueImplLinkedList<>();
                break;
            case DFT: toBeProcessed = new StackImplLinkedList<>();
                break;
        }

        toBeProcessed.insert(new Edge(-1, startingNode));
        while (toBeProcessed.checkFirst()!=null){
            Edge edge = toBeProcessed.pickFirst();
            LinkedList<Integer> neighbors = getNeighbors(edge.target);

            //访问边，排除source为-1 的初始边。
            if(edge.source>=0) {
                visitor.doSomething(edge.source, edge.target,
                        getEdgeValue(edge.source, edge.target));
            }

            //已处理的边，忽略
            if(doneProcessing[edge.target]){
                continue;
            }

            for(int target: neighbors){
                //是无向图，且边的终点为已处理，忽略。即不重复处理无向边。
                if(isUndirected() && doneProcessing[target]){
                    continue;
                }

                //边的终点未处理。则加入到集合中。
                Edge nextEdge = new Edge(edge.target, target);
                if(nextEdge.target!=edge.source) //排除无向边且反向边未处理的情况
                    toBeProcessed.insert(nextEdge);
            }

            //
            doneProcessing[edge.target] = true;
        }
    }

    /**
     * 检查有向图中是否存在环
     * @param path 已经检查过的路径。
     * @param allVertices 未检查的所有顶点。
     */
    default void checkDirectedCycleFromVertex(LinkedList<Integer> path,BinarySearchTree<Integer> allVertices){
        int top=path.getFirst();
        allVertices.deleteValue(top);
        LinkedList<Integer> neighbors = getNeighbors(top);
        for (int n:neighbors) {
            LinkedList<Integer> pathPart = path;

            //已检查路径中是否包含有邻居节点。
            while (pathPart.getLength()>0) {
                int head=pathPart.getFirst();
                if (head == n) {
                    throw new RuntimeException("图中发现了环!");
                }
                pathPart.removeFirst();
            }
            path.appendLast(n);
            checkDirectedCycleFromVertex(path,allVertices);
        }
    }

    /**
     * 检查图中是否有环存在
     * @return
     */
    default boolean detectCycle(){
        BinarySearchTree<Integer> allVertices = getAllVertices();

        try {
            //无向图检查边的终点是否重复出现两次
            if (isUndirected()) {
                while (allVertices.getRoot() != null) {
                    int start = allVertices.getRoot().getValue();
                    RedBlackTree<Integer> metAlready = new RedBlackTree<>();
                    metAlready.insertValue(start);
                    allVertices.deleteValue(start);
                    visitAllConnectedEdges(start,
                            (s, t, v) -> {
                                if(metAlready.searchValue(t) ==
                                        null) {
                                    metAlready.insertValue(t);
                                    allVertices.deleteValue(t);
                                }else if(metAlready.searchValue(s) == null){
                                    metAlready.insertValue(s);
                                    allVertices.deleteValue(s);
                                }else{
                                    //边的终点已经存在，则有环。这里起点已经在前面添加过，所以必然存在。
                                    throw new RuntimeException("发现了环，终点为 "+t);
                                }
                            }, TraversalType.DFT);
                }
            } else {
                while (allVertices.getRoot() != null) {
                    LinkedList list = new LinkedList();
                    list.appendLast(allVertices.getRoot().getValue());
                    checkDirectedCycleFromVertex(list, allVertices);
                }
            }
        }catch (RuntimeException ex){
            return true;
        }
        return false;
    }

    /**
     * 查找最小生成树的辅助类
     */
    class CostEdge extends Edge{
        Integer cost;

        public CostEdge(int source, int target, int cost) {
            super(source, target);
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return cost - ((CostEdge)o).cost;
        }
    }

    /**
     * 最小生成树。参考graph.md
     * @param costFinder
     * @return
     */
    default LinkedList<Edge> minimumSpanningTree(OneArgumentExpression<E,Integer> costFinder){
        if(!isUndirected()){
            throw new IllegalStateException("Spanning tree only applicable to undirected trees");
        }
        LinkedList<Edge> subGraph = new LinkedList<>();
        PriorityQueue<CostEdge> edgeQueue = new PriorityQueue<>((x, y)->x.compareTo(y));
        UnionFind<Integer> unionFind = new UnionFind<>();

        //将边按照权重加入到队列中。PriorityQueue优先返回最小值。
        this.visitAllConnectedEdges(getAllVertices().getRoot().getValue(),
                (s,t,v)-> edgeQueue.add(new CostEdge(s,t,costFinder.compute(v))), TraversalType.DFT);

        //将所有节点加入到unionFind中。
        this.getAllVertices().traversePreOrderNonRecursive((x)-> unionFind.add(x));

        //优先取权重最小的边，查看节点是否相连，未连接则进行连接。
        while(unionFind.getPartitionCount()>1 && edgeQueue.peek()!=null){
            Edge e = edgeQueue.poll();
            int sGroup = unionFind.find(e.source);
            int tGroup = unionFind.find(e.target);
            if(sGroup!=tGroup){
                subGraph.appendLast(e);
                unionFind.union(e.source, e.target);
            }
        }
        return subGraph;
    }
}
