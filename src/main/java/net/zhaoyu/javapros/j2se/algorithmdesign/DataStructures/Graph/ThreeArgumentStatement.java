package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Graph;

@FunctionalInterface
public interface ThreeArgumentStatement<E,F,G> {
    void doSomething(E e, F f, G g);
}
