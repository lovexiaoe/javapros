package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph.MoreSpaceEffecientAdjacencylist;

/**
 * 图中的点对象，记录了value和id。
 * @param <V>
 */
public class GraphVertex<V> implements Comparable<GraphVertex<V>> {
    int id;
    V value;

    public GraphVertex(int id,V value){
        this.id=id;
        this.value=value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex<?> that = (GraphVertex<?>) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(GraphVertex<V> o) {
        return id-o.id;
    }
}
