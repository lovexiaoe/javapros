package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees;

import java.util.*;
import java.util.function.Consumer;

/**
 * @Description:  创建一个树形机构。
 * @Author: zhaoyu
 * @Date: 2021/1/28
 */
public class Tree<E> {
    public static class Node<E> {
        private E value;
        private LinkedList<Node<E>> children;
        private Tree<E> hostTree;
        private Node<E> parent;

        private Node(LinkedList<Node<E>> children, Tree <E>hostTree,E value, Node<E> parent) {
            this.children=children;
            this.hostTree=hostTree;
            this.value=value;
            this.parent=parent;
        }
    }

    private Node<E> root;

    public void addRoot(E value){
        if (root == null) {
            root = new Node<>(new LinkedList<>(), this, value, null);
        } else {
            throw new IllegalStateException("树非空，不能添加根节点");
        }
    }

    public Node<E> getRoot(){
        return root;
    }

    public Node<E> addNode(Node<E> parent,E value){
        if (parent == null) {
            throw new NullPointerException("cannot add child to null parent");
        } else if (parent.hostTree != this) {
            throw new IllegalArgumentException("Parent node  not a part of this tree");
        } else {
            Node<E> newNode=new Node<>(new LinkedList<>(),this,value,parent);
            parent.children.add(newNode);
            return newNode;
        }
    }

    /**
     * 树深度遍历-使用递归方法
     * @param processor
     * @param current
     * @return void
     */
    protected void traverseDepthFirst(Consumer<E> processor, Node<E> current){
        processor.accept(current.value);
        current.children.forEach(n->traverseDepthFirst(processor,n));
    }

    /**
     * 树的遍历通常有深度遍历和广度遍历，深度遍历即优先遍历更深的子节点，广度遍历按照子节点的层级自上向下遍历。
     */

    /**
     * 使用栈进行深度遍历，和递归不同，如果一个节点有两个节点a,b，那么递归处理结果是a,b，而栈处理结果是b,a，和递归的顺序相反。
     * @param consumer
     */
    public void traverseDepthFirstUsingStack(Consumer<E> consumer){
        Stack<Node<E>> stack=new Stack<>();
        //先将根节点放入栈中
        stack.push(getRoot());
        while (stack.peek() != null) {
            //pop一个节点，处理这个节点，如果这个几点有子节点，那么将子节点放入栈中。循环直到所有的节点处理完成。
            Node<E> current = stack.pop();
            consumer.accept(current.value);
            current.children.forEach(c->stack.push(c));
        }
    }

    /**
     * 使用栈进行深度遍历，使用反向列表和递归保持一样的处理顺序。
     * @param consumer
     */
    public void traverseDepthFirstUsingStack1(Consumer<E> consumer){
        Stack<Node<E>> stack=new Stack<>();
        //先将根节点放入栈中
        stack.push(getRoot());
        while (stack.peek() != null) {
            //pop一个节点，处理这个节点，如果这个节点有子节点，那么将子节点放入栈中。循环直到所有的节点处理完成。
            Node<E> current = stack.pop();
            consumer.accept(current.value);

            //反向列表调整加入栈的顺序。
            LinkedList<Node<E>> reverseList = new LinkedList<>();
            current.children.forEach((n)->reverseList.addFirst(n));
            reverseList.forEach(c->stack.push(c));
        }
    }

    /**
     * 当前树的深度遍历
     * @param processor
     * @return void
     */
    public void traverseDepthFirst(Consumer<E> processor){
        traverseDepthFirst(processor,getRoot());
    }

    /**
     * 广度遍历
     * @param consumer
     * @return void
     */
    public void traverseBreadthFirst(Consumer<E> consumer){
        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(getRoot());
        while (queue.peek() != null) {
            Node<E> current= queue.poll();
            consumer.accept(current.value);
            current.children.forEach((n)->queue.add(n));
        }
    }

    /**
     * 创建一个普通的树形结构，参考图tree.png
     */
    public static void main(String[] args) {
        Tree<Integer> tree=new Tree<>();
        tree.addRoot(1);
        Node<Integer> node1=tree.getRoot();
        Node<Integer> node2 = tree.addNode(node1, 5);
        Node<Integer> node3 = tree.addNode(node1, 1);
        Node<Integer> node4 = tree.addNode(node2, 2);
        Node<Integer> node5 = tree.addNode(node2, 5);
        Node<Integer> node6 = tree.addNode(node2, 9);
        Node<Integer> node7 = tree.addNode(node3, 6);
        Node<Integer> node8 = tree.addNode(node3, 2);
        Node<Integer> node9 = tree.addNode(node5, 5);
        Node<Integer> node10 = tree.addNode(node6, 9);
        Node<Integer> node11 = tree.addNode(node6, 6);

    }
}
