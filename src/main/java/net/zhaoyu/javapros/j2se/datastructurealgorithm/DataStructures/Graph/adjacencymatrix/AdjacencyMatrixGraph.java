package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.adjacencymatrix;

import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.Graph;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Lists.LinkedList;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinarySearchTree;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.RedBlack.RedBlackTree;

import java.util.Arrays;


public class AdjacencyMatrixGraph<V,E> implements Graph<V,E> {

    /** 空边的表示，在实际的图中并不存在，只是为了表示方便。 */
    private static class NullEdgeValue{}

    /** 空边的表示 */
    private NullEdgeValue nullEdge=new NullEdgeValue();
    /** 空顶点的表示 */
    private NullEdgeValue nullVertex=new NullEdgeValue();

    /** 是否无向 */
    boolean undirected;

    /** 节点数组 */
    Object[] vertexValues = new Object[0];

    /** 邻接矩阵 */
    Object[][] adjacencyMatrix = new Object[0][];

    public AdjacencyMatrixGraph(boolean undirected) {
        this.undirected = undirected;
    }

    @Override
    public int addVertex() {
        int numVertices = adjacencyMatrix.length;

        //一个新节点加入时，将阵列扩展一行，和一列。
        Object[][] newAdjacencyMatrix = new Object[numVertices + 1][];
        for (int i = 0; i < numVertices; i++) {
            newAdjacencyMatrix[i] = new Object[numVertices + 1];
            System.arraycopy(adjacencyMatrix[i],0,newAdjacencyMatrix[i],0,numVertices);
        }
        newAdjacencyMatrix[numVertices] = new Object[numVertices + 1];
        adjacencyMatrix = newAdjacencyMatrix;

        //将节点加入到节点数组。
        Object [] vertexValuesNew = new Object[vertexValues.length+1];
        System.arraycopy(vertexValues,0,vertexValuesNew,0,vertexValues.length);
        vertexValuesNew[vertexValues.length]=nullVertex;
        vertexValues = vertexValuesNew;

        //返回id
        return numVertices;
    }

    @Override
    public void removeVertex(int id) {
        vertexValues[id]=0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            adjacencyMatrix[i][id] = null;
            adjacencyMatrix[id][i] = null;
        }
    }

    @Override
    public void addEdge(int source, int target) {
        if (adjacencyMatrix[source][target] == null) {
            adjacencyMatrix[source][target] = nullEdge;
            if (undirected) {
                adjacencyMatrix[target][source] = nullEdge;
            }
        } else {
            throw new IllegalArgumentException("边已经存在。");
        }
    }

    @Override
    public void removeEdge(int source, int target) {
        adjacencyMatrix[source][target] = null;
        if (undirected) {
            adjacencyMatrix[target][source] = null;
        }
    }

    @Override
    public boolean isAdjacent(int source, int target) {
        return adjacencyMatrix[source][target]!=null;
    }

    @Override
    public LinkedList<Integer> getNeighbors(int source) {
        LinkedList<Integer> neighborList = new LinkedList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[source][i]!=null) {
                neighborList.appendLast(i);
            }
        }
        return neighborList;
    }

    @Override
    public void setVertexValue(int vertex, V value) {
        vertexValues[vertex] = value;
    }

    @Override
    public V getVertexValue(int vertex) {
        if (vertexValues[vertex] != nullVertex) {
            return (V) vertexValues[vertex];
        } else {
            throw new IllegalArgumentException("Vertex "+vertex+"does not exist。");
        }
    }

    @Override
    public void setEdgeValue(int source, int target, E value) {
        adjacencyMatrix[source][target] = value;
        if (undirected) {
            adjacencyMatrix[target][source]=value;
        }
    }

    public void addEdge(int source, int target, E value) {
        addEdge(source, target);
        setEdgeValue(source, target, value);
    }

    @Override
    public E getEdgeValue(int source, int target) {
        if (adjacencyMatrix[source][target] != nullEdge) {
            return (E) adjacencyMatrix[source][target];
        } else {
            return null;
        }
    }

    @Override
    public boolean isUndirected() {
        return undirected;
    }

    @Override
    public BinarySearchTree<Integer> getAllVertices() {
        BinarySearchTree bst = new RedBlackTree();
        for (int i = 0; i < vertexValues.length; i++) {
            if (vertexValues[i] != null) {
                bst.insertValue(i);
            }
        }
        return bst;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{vertexValues=" + Arrays.toString(vertexValues) + "\n adjacencyMatrix=\n");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            sb.append(Arrays.toString(adjacencyMatrix[i])+"\n");
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int maxVertexID() {
        return vertexValues.length-1;
    }

    public static void main(String[] args) {
        AdjacencyMatrixGraph<Integer, String> graph1 = new AdjacencyMatrixGraph<>(true);
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
