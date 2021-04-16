package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph;

public interface Stack<E> extends OrderedStore<E> {
    void push(E value);
    E pop();
    E peek();
    @Override
    default void insert(E value) {
       push(value);
    }

    @Override
    default E pickFirst() {
        return pop();
    }

    @Override
    default E checkFirst() {
        return peek();
    }
}
