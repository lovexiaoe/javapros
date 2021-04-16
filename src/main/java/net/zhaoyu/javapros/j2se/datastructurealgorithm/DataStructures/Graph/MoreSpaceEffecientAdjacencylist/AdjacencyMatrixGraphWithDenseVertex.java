package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.MoreSpaceEffecientAdjacencylist;

import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.Graph;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Lists.LinkedList;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinarySearchTree;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinaryTree;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.RedBlack.RedBlackTree;

import java.util.Arrays;


/**
 * 代码实际运行可能还有问题-有时间优化更行
 *
 * 对于AdjacencyMatrixGraph中的图，有一些问题，我们在删除点后，不能回收点占用的空间。如果我们做了回收，那么后面添加的点的序列就会发生变化。
 * 为了避免这种情况，我们需要将点的id和序列分离，并且能够根据点的id返回一个点的序列。可以使用一个bst树来存储点（GraphVertex）。
 * @param <V>
 * @param <E>
 */
public class AdjacencyMatrixGraphWithDenseVertex<V, E> implements Graph<V, E> {

    class Vertex extends GraphVertex<V> {
        /** 在矩阵中的序列 */
        int internalIndex;

        public Vertex(int id, V value, int internalIndex) {
            super(id, value);
            this.internalIndex=internalIndex;
        }

        public int getInternalIndex(){
            return internalIndex;
        }

        public void setInternalIndex(int internalIndex) {
            this.internalIndex = internalIndex;
        }
    }

    /** id序列号 */
    private int nextId;

    /** 用于表示空的点和边 */
    private static class NullValue{}
    private NullValue nullEdge = new NullValue();

    Object[][] adjacencyMatrix = new Object[0][];

    RedBlackTree<GraphVertex<V>> vertices = new RedBlackTree<>();
    boolean undirected;
    public AdjacencyMatrixGraphWithDenseVertex(boolean undirected){
        this.undirected = undirected;
    }

    @Override
    public int addVertex() {
        int id=nextId++;
        int numVertices=adjacencyMatrix.length;

        //一个新节点加入时，将阵列扩展一行，和一列。
        Object[][] newAdjacencyMatrix = new Object[numVertices + 1][];
        for (int i = 0; i < numVertices; i++) {
            newAdjacencyMatrix[i] = new Object[numVertices + 1];
            System.arraycopy(adjacencyMatrix[i],0,newAdjacencyMatrix[i],0,numVertices);
        }
        newAdjacencyMatrix[numVertices] = new Object[numVertices + 1];

        //将节点加入到节点数组。
        vertices.insertValue(new Vertex(id, null, adjacencyMatrix.length));

        adjacencyMatrix = newAdjacencyMatrix;
        //返回id
        return numVertices;

    }

    @Override
    public void removeVertex(int id) {
        BinaryTree.Node<GraphVertex<V>> node = vertices.searchValue(new GraphVertex<V>(id, null));

        //如果节点存在，
        if (node != null) {
            int internalId = ((Vertex) (node.getValue())).getInternalIndex();
            int numVertices = adjacencyMatrix.length;
            Object[][] newAdjacencyMatrix = new Object[numVertices - 1][];

            //从原矩阵中复制删除该节点关联的行和列，然后copy到新矩阵。
            for (int i = 0; i < internalId; i++) {
                newAdjacencyMatrix[i] = new Object[numVertices - 1];
                System.arraycopy(adjacencyMatrix[i], 0, newAdjacencyMatrix[i], 0, internalId);
                System.arraycopy(adjacencyMatrix[i], internalId + 1, newAdjacencyMatrix[i], internalId,
                        numVertices - internalId - 1);
            }
            for (int i = internalId + 1; i < numVertices; i++) {
                newAdjacencyMatrix[i - 1] = new Object[numVertices - 1];
                System.arraycopy(adjacencyMatrix[i], 0, newAdjacencyMatrix[i - 1], 0, internalId);
                System.arraycopy(adjacencyMatrix[i], internalId + 1, newAdjacencyMatrix[i - 1], internalId,
                        numVertices - internalId - 1);
            }
            adjacencyMatrix = newAdjacencyMatrix;

            //删除顶点，
            vertices.traversePreOrderNonRecursive((gv) -> {
                if (((Vertex) gv).getInternalIndex() > internalId)
                    ((Vertex) gv).setInternalIndex(((
                            Vertex) gv).getInternalIndex() - 1);
            });
            vertices.deleteValue(new GraphVertex<>(id, null));
        } else {
            throw new IllegalArgumentException("Vertex with id " + id + "does not exist");
        }
    }

    @Override
    public void addEdge(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(
                new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(
                new GraphVertex<V>(target, null));
        if(sNode!=null && tNode!=null) {
            int s = ((Vertex)(sNode.getValue())).getInternalIndex();
            int t = ((Vertex)(tNode.getValue())).getInternalIndex();
            if(adjacencyMatrix[s][t] == null){
                adjacencyMatrix[s][t] = nullEdge;
                if(undirected){
                    adjacencyMatrix[t][s] = nullEdge;
                }
            }else{
                throw new IllegalArgumentException("Edge already exists");
            }
        }else{
            throw new IllegalArgumentException("Non-existent ID");
        }

    }

    public void addEdge(int source, int target, E value) {
        addEdge(source, target);
        setEdgeValue(source, target, value);
    }

    @Override
    public void removeEdge(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(
                new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(
                new GraphVertex<V>(target, null));
        if(sNode!=null && tNode!=null) {
            int s = ((Vertex)(sNode.getValue())).getInternalIndex();
            int t = ((Vertex)(tNode.getValue())).getInternalIndex();
            adjacencyMatrix[s][t] = null;
        }else{
            throw new IllegalArgumentException("Non-existent ID");
        }
    }

    @Override
    public boolean isAdjacent(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(
                new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(
                new GraphVertex<V>(target, null));
        if(sNode!=null && tNode!=null) {
            int s = ((Vertex)(sNode.getValue())).getInternalIndex();
            int t = ((Vertex)(tNode.getValue())).getInternalIndex();
            return adjacencyMatrix[s][t] != null;
        }else{
            throw new IllegalArgumentException("Non-existent ID");
        }
    }

    @Override
    public LinkedList<Integer> getNeighbors(int source) {
        BinaryTree.Node<GraphVertex<V>> node = vertices.searchValue(
                new GraphVertex<V>(source, null));
        if(node!=null){
            LinkedList<Integer> neighborsList = new LinkedList<>();
            int sourceInternalIndex = ((Vertex)
                    node.getValue()).getInternalIndex();
            vertices.traverseInOrderNonRecursive((gv)->{
                int targetInternalIndex = ((Vertex) gv)
                        .getInternalIndex();
                if(adjacencyMatrix[sourceInternalIndex]
                        [targetInternalIndex]!=null)
                    neighborsList.appendLast(gv.getId());
            });
            return neighborsList;
        }else{
            throw new IllegalArgumentException("Vertex with id "+source+" does not exist");
        }
    }

    @Override
    public void setVertexValue(int vertex, V value) {
        BinaryTree.Node<GraphVertex<V>> node =
                vertices.searchValue(
                        new GraphVertex<V>(vertex, null));
        if(node!=null){
            node.getValue().setValue(value);
        }else{
            throw new IllegalArgumentException("Vertex with id "+vertex+" does not exist");
        }
    }

    @Override
    public V getVertexValue(int vertex) {
        BinaryTree.Node<GraphVertex<V>> node =
                vertices.searchValue(
                        new GraphVertex<V>(vertex, null));
        if(node!=null){
            return node.getValue().getValue();
        }else{
            throw new IllegalArgumentException("Vertex with id "+vertex+" does not exist");
        }
    }

    @Override
    public void setEdgeValue(int source, int target, E value) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(
                new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(
                new GraphVertex<V>(target, null));
        if(sNode!=null && tNode!=null) {
            int s = ((Vertex)(sNode.getValue())).getInternalIndex();
            int t = ((Vertex)(tNode.getValue())).getInternalIndex();
            adjacencyMatrix[s][t] = value;
            if (undirected) {
                adjacencyMatrix[t][s] = value;
            }
        }else{
            throw new IllegalArgumentException("Non-existent ID");
        }
    }

    @Override
    public E getEdgeValue(int source, int target) {
        BinaryTree.Node<GraphVertex<V>> sNode = vertices.searchValue(
                new GraphVertex<V>(source, null));
        BinaryTree.Node<GraphVertex<V>> tNode = vertices.searchValue(
                new GraphVertex<V>(target, null));
        if(sNode!=null && tNode!=null) {
            int s = ((Vertex)(sNode.getValue())).getInternalIndex();
            int t = ((Vertex)(tNode.getValue())).getInternalIndex();
            return (E) adjacencyMatrix[s][t];
        }else{
            throw new IllegalArgumentException("Non-existent ID");
        }
    }

    @Override
    public boolean isUndirected() {
        return undirected;
    }

    @Override
    public BinarySearchTree<Integer> getAllVertices() {
        BinarySearchTree<Integer> allVertices = new RedBlackTree<>();
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
        StringBuilder sb = new StringBuilder("{vertexValues=" );
        vertices.traverseInOrderNonRecursive(d->sb.append(d.value));
        sb.append("\n adjacencyMatrix=\n");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            sb.append(Arrays.toString(adjacencyMatrix[i])+"\n");
        }
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjacencyMatrixGraphWithDenseVertex<Integer, String> graph1 = new AdjacencyMatrixGraphWithDenseVertex<>(true);
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
