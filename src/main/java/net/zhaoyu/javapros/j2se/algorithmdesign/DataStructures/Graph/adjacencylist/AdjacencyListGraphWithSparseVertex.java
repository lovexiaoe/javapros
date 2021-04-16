package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph.adjacencylist;

import zhaoyu.DataStructures.Graph.Graph;
import zhaoyu.DataStructures.Graph.MoreSpaceEffecientAdjacencylist.GraphVertex;
import zhaoyu.DataStructures.Lists.DoublyLinkedList2;
import zhaoyu.DataStructures.Lists.LinkedList;
import zhaoyu.DataStructures.Trees.BST.BinarySearchTree;
import zhaoyu.DataStructures.Trees.BST.BinaryTree;
import zhaoyu.DataStructures.Trees.RedBlack.RedBlackTree;


/**
 * 图的邻接表表示，用数组的形式表示稀疏图
 */
public class AdjacencyListGraphWithSparseVertex<V,E> implements Graph<V,E> {
    boolean undirected;

    public AdjacencyListGraphWithSparseVertex(boolean undirected) {
        this.undirected = undirected;
    }

    class Edge implements Comparable<Edge>{
        E value;
        int target;
        DoublyLinkedList2.DoublyLinkedNode<Integer> targetNode;

        public Edge(int target) {
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            return target == edge.target;

        }

        @Override
        public int hashCode() {
            return target;
        }

        @Override
        public int compareTo(Edge o) {
            return target - o.target;
        }

        @Override
        public String toString() {
            return  "--" + value +"-->{"+ target+"," + targetNode.getValue() + '}';
        }
    }
    class Vertex extends GraphVertex<V> {
        //每个顶点拥有多条边，这些边存储于红黑树中。
        RedBlackTree<Edge> edges = new RedBlackTree<>();
        //记录邻居节点id，方便操作。
        DoublyLinkedList2<Integer> neighbors = new DoublyLinkedList2<>();
        public Vertex(int id, V value) {
            super(id, value);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{").append(this.getId()).append(",").append(this.getValue()).append("}");
            edges.traverseInOrderNonRecursive(e->sb.append(e.toString()));
            return sb.toString();
        }
    }

    //使用数组表示稀疏图的邻接表。
    Object[] vertices = new Object[0];

    /**
     * 扩展顶点数组。
     * @return
     */
    @Override
    public int addVertex() {
        Object[] newVertices = new Object[vertices.length+1];
        System.arraycopy(vertices, 0, newVertices, 0, vertices.length);
        newVertices[vertices.length] = new Vertex(vertices.length, null);
        vertices=newVertices;
        return newVertices.length-1;
    }

    @Override
    public void removeVertex(int id) {

        Vertex sVertex = (Vertex) vertices[id];
        if(sVertex==null){
            throw new IllegalArgumentException("Vertex "+ id +" does not exist");
        }
        LinkedList<Integer> neighbors = getNeighbors(id);
        Edge dummyEdgeForId = new Edge(id);
        for(int t:neighbors){
            Edge e = ((Vertex)vertices[t]).edges.deleteValue(dummyEdgeForId).getValue();
            ((Vertex)vertices[t]).neighbors.removeNode(e.targetNode);
        }
        vertices[id] = null;
    }

    @Override
    public void addEdge(int source, int target) {
        Vertex sVertex = (Vertex) vertices[source];
        Edge sEdge = sVertex.edges.insertValue(new Edge(target)).getValue();
        sEdge.targetNode =
                (DoublyLinkedList2.DoublyLinkedNode<Integer>) sVertex.neighbors.appendLast(sEdge.target);
        if(undirected){
            Vertex tVertex = (Vertex) vertices[target];
            Edge tEdge = tVertex.edges.insertValue(new Edge(source)).getValue();
            tEdge.targetNode =
                    (DoublyLinkedList2.DoublyLinkedNode<Integer>) tVertex.neighbors.appendLast(tEdge.target);
        }
    }

    public void addEdge(int source, int target, E value) {
        addEdge(source, target);
        setEdgeValue(source, target, value);
    }

    @Override
    public void removeEdge(int source, int target) {
        Vertex sVertex = (Vertex) vertices[source];
        Edge deletedEdge = sVertex.edges.deleteValue(new Edge(target)).getValue();
        sVertex.neighbors.removeNode(deletedEdge.targetNode);
        if(undirected){
            Vertex tVertex = (Vertex) vertices[target];
            deletedEdge = tVertex.edges.deleteValue(new Edge(source)).getValue();
            tVertex.neighbors.removeNode(deletedEdge.targetNode);
        }
    }

    @Override
    public boolean isAdjacent(int source, int target) {
        Vertex sVertex = (Vertex) vertices[source];
        return sVertex.edges.searchValue(new Edge(target))!=null;
    }

    @Override
    public LinkedList<Integer> getNeighbors(int source) {
        Vertex sVertex = (Vertex) vertices[source];
        return sVertex.neighbors;
    }

    @Override
    public void setVertexValue(int vertex, V value) {
        Vertex sVertex = (Vertex) vertices[vertex];
        if(sVertex==null){
            throw new IllegalArgumentException("Vertex "+ vertex + "does not exist");
        }else{
            sVertex.setValue(value);
        }
    }

    @Override
    public V getVertexValue(int vertex) {
        Vertex sVertex = (Vertex) vertices[vertex];
        if(sVertex==null){
            throw new IllegalArgumentException("Vertex "+ vertex + "does not exist");
        }else{
            return sVertex.getValue();
        }
    }

    @Override
    public void setEdgeValue(int source, int target, E value) {
        Vertex sVertex = (Vertex) vertices[source];
        Vertex tVertex = (Vertex) vertices[target];
        if(sVertex==null){
            throw new IllegalArgumentException("Vertex "+ source + "does not exist");
        }else if(tVertex==null){
            throw new IllegalArgumentException("Vertex "+ target + "does not exist");
        }else{
            BinaryTree.Node<Edge> node =
                    sVertex.edges.searchValue(new Edge(target));
            if(node==null){
                throw new IllegalArgumentException("Edge between "+ source + "and"
                        + target + "does not exist");
            }else{
                node.getValue().value = value;
            }
        }
    }

    @Override
    public E getEdgeValue(int source, int target) {
        Vertex sVertex = (Vertex) vertices[source];
        Vertex tVertex = (Vertex) vertices[target];
        if(sVertex==null){
            throw new IllegalArgumentException("Vertex "+ source + "does not exist");
        }else if(tVertex==null){
            throw new IllegalArgumentException("Vertex "+ target + "does not exist");
        }else{
            BinaryTree.Node<Edge> node =
                    sVertex.edges.searchValue(new Edge(target));
            if(node==null){
                throw new IllegalArgumentException("Edge between "+ source + "and"
                        + target + "does not exist");
            }else{
                return node.getValue().value;
            }
        }
    }

    @Override
    public boolean isUndirected() {
        return undirected;
    }

    @Override
    public BinarySearchTree<Integer> getAllVertices() {
        BinarySearchTree<Integer> allVertices = new RedBlackTree<>();
        for(int i=0;i<vertices.length;i++){
            if(vertices[i]!=null){
                allVertices.insertValue(i);
            }
        }
        return allVertices;
    }

    @Override
    public int maxVertexID() {
        return vertices.length-1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("undirected=" );
        sb.append(undirected);
        sb.append("\n adjacencyList=\n");
        for (int i = 0; i < vertices.length; i++) {
            sb.append(((Vertex)vertices[i]).toString()).append("\n");
        }
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjacencyListGraphWithSparseVertex<Integer, String> graph1 = new AdjacencyListGraphWithSparseVertex<>(true);
        graph1.setVertexValue(graph1.addVertex(), 0);
        graph1.setVertexValue(graph1.addVertex(), 1);
        graph1.setVertexValue(graph1.addVertex(), 2);
        graph1.setVertexValue(graph1.addVertex(), 3);
        graph1.setVertexValue(graph1.addVertex(), 4);
        graph1.setVertexValue(graph1.addVertex(), 5);

        graph1.addEdge(0,1,"01");
        graph1.addEdge(0,2,"02");
        graph1.addEdge(0,3,"03");
        graph1.addEdge(0,4,"04");
        graph1.addEdge(1,4,"14");
        graph1.addEdge(2,4,"24");
        graph1.addEdge(2,5,"25");
        graph1.addEdge(3,5,"35");
        graph1.addEdge(4,5,"45");

        LinkedList<Integer> vertex2Neighbours = graph1.getNeighbors(2);
        System.out.println(vertex2Neighbours);
        System.out.println(graph1);
    }
}
