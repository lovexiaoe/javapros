package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph;

public interface OrderedStore<E> extends Iterable<E>{
    void insert(E value);
    E pickFirst();
    E checkFirst();
}
