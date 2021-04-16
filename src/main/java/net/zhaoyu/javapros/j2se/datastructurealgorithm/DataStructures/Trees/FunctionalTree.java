package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees;

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * @Description: 树的抽象数类型，一个树的抽象数据类型（ADT-abstract data type）可以用多种方式定义。当树可变时，我们可以定义为如下的模型：
 * 1.获取根节点。
 * 2.给出一个节点，获取它的子节点。
 * 以上特点是一个树的模型必须拥有的。还可以定义其他的防范。
 *
 * 也可以通过递归定义一个树的抽象数据类型。如下：
 * 一个树是包含了如下元素的有序列表：
 *  1.一个值。
 *  2.它的子树的列表。
 * 下面的类，实现了这种抽象数据类型。
 *
 * @Author: zhaoyu
 * @Date: 2021/2/22
 */
public class FunctionalTree<E> {
    private E value;
    private LinkedList<FunctionalTree<E>> children;

    public FunctionalTree(E value,LinkedList<FunctionalTree<E>> children){
        this.children=children;
        this.value=value;
    }

    public LinkedList<FunctionalTree<E>> getChildren(){
        return children;
    }

    public E getValue(){
        return value;
    }

    /**
     * 递归深度遍历
     * @param consumer
     * @return void
     */
    public void traverseDepthFirst(Consumer<E> consumer){
        consumer.accept(value);
        children.forEach((n)->n.traverseDepthFirst(consumer));
    }

    public static void main(String[] args) {

        FunctionalTree<Integer> t1 = new FunctionalTree<>(5, new LinkedList<>());
        FunctionalTree<Integer> t2 = new FunctionalTree<>(9, new LinkedList<>());
        FunctionalTree<Integer> t3 = new FunctionalTree<>(6, new LinkedList<>());

        FunctionalTree<Integer> t4 = new FunctionalTree<>(2, new LinkedList<>());
        LinkedList<FunctionalTree<Integer>> children5 = new LinkedList<>();
        children5.add(t1);
        FunctionalTree<Integer> t5 = new FunctionalTree<>(5,children5);
        LinkedList<FunctionalTree<Integer>> children6 = new LinkedList<>();
        children6.add(t3);
        children6.add(t2);
        FunctionalTree<Integer> t6 = new FunctionalTree<>(9, children6);
        FunctionalTree<Integer> t7 = new FunctionalTree<>(6, new LinkedList<>());
        FunctionalTree<Integer> t8 = new FunctionalTree<>(2, new LinkedList<>());
        LinkedList<FunctionalTree<Integer>> children9 = new LinkedList<>();
        children9.add(t4);
        children9.add(t5);
        children9.add(t6);
        FunctionalTree<Integer> t9 = new FunctionalTree<>(5,children9);
        LinkedList<FunctionalTree<Integer>> children10 = new LinkedList<>();
        children10.add(t7);
        children10.add(t8);
        FunctionalTree<Integer> t10 = new FunctionalTree<>(1,children10);

        LinkedList<FunctionalTree<Integer>> children = new LinkedList<>();
        children.add(t9);
        children.add(t10);
        FunctionalTree<Integer> tree = new FunctionalTree<>(1,children);

        tree.traverseDepthFirst(System.out::print);

        System.out.println();
    }
}
