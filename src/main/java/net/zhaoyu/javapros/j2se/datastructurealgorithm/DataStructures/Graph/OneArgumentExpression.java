package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph;

/**
 * Created by debasishc on 28/8/16.
 */
@FunctionalInterface
public interface OneArgumentExpression<A,R> {
    R compute(A a);
}
