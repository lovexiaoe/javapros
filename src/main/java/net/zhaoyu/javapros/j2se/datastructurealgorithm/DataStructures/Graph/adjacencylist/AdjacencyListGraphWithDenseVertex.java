package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.adjacencylist;

import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.Graph;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.MoreSpaceEffecientAdjacencylist.GraphVertex;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Lists.DoublyLinkedList2;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Lists.LinkedList;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinarySearchTree;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinaryTree;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.RedBlack.RedBlackTree;


/**
 * 图的邻接表表示，用BST存储顶点，来表示稠密顶点。这可以让我们在删除一个顶点来恢复空间的时候不引起其他顶点id的变化。
 */
public class AdjacencyListGraphWithDenseVertex<V,E> implements Graph<V,E> {

    /**
     * 下个被插入顶点的id序列号。
     */
    int nextId;
    boolean undirected;

    public AdjacencyListGraphWithDenseVertex(boolean undirected) {
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
        RedBlackTree<Edge>
                edges = new RedBlackTree<>();
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

    //使用红黑树表示稠密图的邻接表。让红黑树去管理空间。
    RedBlackTree<GraphVertex<V>> vertices = new RedBlackTree<>();
    @Override
    public int addVertex() {
        vertices.insertValue(new Vertex(nextId, null));
        return nextId++;
    }

    @Override
    public void removeVertex(int id) {
        vertices.deleteValue(new GraphVertex<V>(id, null));
        vertices.traverseInOrderNonRecursive((gv)->{
                    BinaryTree.Node<Edge> edgeNode = ((Vertex) gv).edges.deleteValue(new Edge(id));
                    if(edgeNode!=null){
                        Edge edge = edgeNode.getValue();
                        ((Vertex) gv).neighbors.removeNode(edge.targetNode);
                    }
                });
    }

    @Override
    public void addEdge(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new Vertex(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(new Vertex(target, null));
        if (sNode == null) {
            throw new IllegalArgumentException("ID 为" +source+" 的顶点不存在");
        } else if (tNode == null) {
            throw new IllegalArgumentException("ID 为" + target + " 的顶点不存在");
        } else {
            Vertex sVertex = (Vertex) sNode.getValue();
            Vertex tVertex =(Vertex) tNode.getValue();

            //新增边
            Edge tEdge = new Edge(target);
            sVertex.edges.insertValue(tEdge);

            //增加邻居，并记录边的目标节点。
            tEdge.targetNode = (DoublyLinkedList2.DoublyLinkedNode<Integer>)
                    sVertex.neighbors.appendLast(tVertex.getId());

            if (undirected) {
                //新增边
                Edge sEdge = new Edge(source);
                tVertex.edges.insertValue(sEdge);

                //增加邻居，并记录边的目标节点。
                sEdge.targetNode = (DoublyLinkedList2
                        .DoublyLinkedNode<Integer>) tVertex.neighbors
                        .appendLast(sVertex.getId());
            }

        }
    }

    public void addEdge(int source, int target, E value) {
        addEdge(source, target);
        setEdgeValue(source, target, value);
    }

    @Override
    public void removeEdge(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new Vertex(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(new Vertex(target, null));
        if (sNode == null) {
            throw new IllegalArgumentException("ID 为" +source+" 的顶点不存在");
        } else if (tNode == null) {
            throw new IllegalArgumentException("ID 为" + target + " 的顶点不存在");
        } else {
            Vertex sVertex = (Vertex) sNode.getValue();
            Edge deletedEdge = sVertex.edges.deleteValue(new Edge(target)).getValue();
            sVertex.neighbors.removeNode(deletedEdge.targetNode);

            if(undirected) {
                Vertex tVertex = (Vertex) tNode.getValue();
                deletedEdge = tVertex.edges.deleteValue(new Edge(source)).getValue();
                tVertex.neighbors.removeNode(deletedEdge.targetNode);
            }
        }
    }

    @Override
    public boolean isAdjacent(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(new GraphVertex<V>(target, null));
        if(sNode == null){
            throw new IllegalArgumentException("Vertex ID " +source+" does not exist");
        }else if(tNode == null){
            throw new IllegalArgumentException("Vertex ID " +target+" does not exist");
        }else{
            Vertex sVertex = (Vertex) sNode.getValue();
            return sVertex.edges.searchValue(new Edge(target)) != null;
        }
    }

    @Override
    public LinkedList<Integer> getNeighbors(int source) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new GraphVertex<V>(source, null));
        if(sNode == null){
            throw new IllegalArgumentException("Vertex ID "+source+" does not exist");
        }else{
            Vertex sVertex = (Vertex) sNode.getValue();
            return  sVertex.neighbors;
        }
    }

    @Override
    public void setVertexValue(int vertex, V value) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new GraphVertex<V>(vertex, null));
        if(sNode == null){
            throw new IllegalArgumentException("Vertex ID " +vertex+" does not exist");
        }else{
            Vertex sVertex = (Vertex) sNode.getValue();
            sVertex.setValue(value);
        }
    }

    @Override
    public V getVertexValue(int vertex) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new GraphVertex<V>(vertex, null));
        if(sNode == null){
            throw new IllegalArgumentException("Vertex ID " +vertex+" does not exist");
        }else{
            Vertex sVertex = (Vertex) sNode.getValue();
            return sVertex.getValue();
        }
    }

    @Override
    public void setEdgeValue(int source, int target, E value) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(new GraphVertex<V>(target, null));
        if(sNode == null){
            throw new IllegalArgumentException("Vertex ID "+source+" does not exist");
        }else if(tNode == null){
            throw new IllegalArgumentException("Vertex ID "+target+" does not exist");
        }else{
            Vertex sVertex = (Vertex) sNode.getValue();
            BinaryTree.Node<Edge> edgeNode = sVertex.edges.searchValue(new Edge(target));
            if(edgeNode!=null) {
                edgeNode.getValue().value = value;
                if (undirected) {
                    Vertex tVertex = (Vertex) tNode.getValue();
                    edgeNode = tVertex.edges.searchValue(new Edge(source));
                    edgeNode.getValue().value = value;
                }
            }else{
                throw new IllegalArgumentException("No edge exists between the vertices " + source + " and " + target);
            }
        }
    }

    @Override
    public E getEdgeValue(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(new GraphVertex<V>(target, null));
        if(sNode == null){
            throw new IllegalArgumentException("Vertex ID "+source+" does not exist");
        }else if(tNode == null){
            throw new IllegalArgumentException("Vertex ID "+target+" does not exist");
        }else{
            Vertex sVertex = (Vertex) sNode.getValue();
            BinaryTree.Node<Edge> edgeNode = sVertex.edges.searchValue(new Edge(target));
            if(edgeNode!=null) {
                return edgeNode.getValue().value;
            }else{
                throw new IllegalArgumentException("No edge exists between the vertices " + source + " and " + target);
            }
        }
    }

    @Override
    public boolean isUndirected() {
        return undirected;
    }

    @Override
    public BinarySearchTree<Integer> getAllVertices() {
        BinarySearchTree<Integer> allVertices
                = new RedBlackTree<>();
        vertices.traversePreOrderNonRecursive(
                (v) -> allVertices.insertValue(v.getId()));
        return allVertices;
    }

    @Override
    public int maxVertexID() {
        return nextId-1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("undirected=" );
        sb.append(undirected);
        sb.append("\n adjacencyList=\n");
        vertices.traverseInOrderNonRecursive(v->sb.append(v.toString()).append("\n"));
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjacencyListGraphWithDenseVertex<Integer, String> graph1 = new AdjacencyListGraphWithDenseVertex<>(true);
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
