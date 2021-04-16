package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph;

public interface Queue<E> extends OrderedStore<E> {
    void enqueue(E value);
    E dequeue();
    E peek();
    @Override
    default void insert(E value) {
       enqueue(value);
    }

    @Override
    default E pickFirst() {
        return dequeue();
    }

    @Override
    default E checkFirst() {
        return peek();
    }
}
