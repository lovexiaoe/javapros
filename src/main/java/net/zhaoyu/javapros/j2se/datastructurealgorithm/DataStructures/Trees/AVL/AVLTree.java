package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.AVL;


import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinarySearchTree;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinaryTree;

/**
 * @Description: AVL是一种平衡二叉搜索树。旋转（rotate）不会破会二叉搜索树的特性，AVL就是通过旋转保证AVL的平衡。
 *      AVL需要判断节点告诉是否平衡，所以需要记录节点的高度。
 * @Author: zhaoyu
 * @Date: 2021/1/26
 */
public class AVLTree <E extends Comparable<E>> extends BinarySearchTree<E> {
    public static class Node<E extends Comparable<E>> extends BinaryTree.Node{
        //树的高度，根节点为最大，叶节点为最小
        protected  int height=0;
        public Node(BinaryTree.Node parent, BinaryTree containerTree, E value) {
            super(parent, containerTree, value);
        }
    }

    /**
     * 树高度-空节点时返回0
     * @param node
     * @return int
     */
    private int nullSafeHeight(Node<E> node){
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
    }

    /**
     * 计算和更新子树的高度。
     * @param node
     * @return void
     */
    private void nullSafeComputeHeight(Node<E> node){
        Node<E> left= (Node<E>) node.getLeft();
        Node<E> right= (Node<E>) node.getRight();
        int leftHeight=left==null?0:left.height;
        int rightHeight = right == null ? 0 : right.height;
        node.height = Math.max(leftHeight, rightHeight)+1;
    }

    /**
     * AVL的旋转，并重新计算高度。
     * @param node
     * @param left
     * @return void
     */
    @Override
    protected void rotate(BinaryTree.Node<E> node,boolean left){
        Node<E> n= (Node<E>) node;
        Node<E> child;

        //父级方法中，传入的是子节点。所以这里查找子节点并传入。
        if (left) {
            child = (Node<E>) n.getRight();
        } else {
            child = (Node<E>) n.getLeft();
        }
        super.rotate(child,left);

        //节点和子节点的高度发生变化，重新计算。
        if (node != null) {
            nullSafeComputeHeight(n);
        }
        if (child != null) {
            nullSafeComputeHeight(child);
        }
    }

    /**
     * 树的重新平衡，从一个节点一路到根节点。检查左子树和右子树的高度差，如果超过1，则进行平衡。
     * @param
     * @return void
     */
    protected void rebalance(Node<E> node){
        if (node == null) {
            return;
        }
        nullSafeComputeHeight(node);
        int leftHeight = nullSafeHeight((Node<E>) node.getLeft());
        int rightHeight = nullSafeHeight((Node<E>) node.getRight());
        switch (leftHeight - rightHeight) {
            case -1:
            case 0:
            case 1:
                rebalance((Node<E>) node.getParent());
                break;
            case 2:
                int childLeftHeight = nullSafeHeight((Node<E>) node.getLeft().getLeft());
                int childRightHeight = nullSafeHeight((Node<E>) node.getLeft().getLeft());
                //两次旋转的情况，先向反方向旋转一次
                if (childRightHeight > childLeftHeight) {
                    rotate(node.getLeft(),true);
                }
                Node<E> oldParent = (Node<E>) node.getParent();
                rotate(node,false);
                rebalance(oldParent);
                break;
            case -2:
                childLeftHeight = nullSafeHeight((Node<E>) node.getRight().getLeft());
                childRightHeight = nullSafeHeight((Node<E>) node.getRight().getRight());
                if (childLeftHeight > childRightHeight) {
                    rotate(node.getRight(), false);
                }
                oldParent = (Node<E>) node.getParent();
                rotate(node, true);
                rebalance(oldParent);
                break;
            default:
        }
    }

    /**
     * 重写插入节点
     * @param value
     * @return zhaoyu.DataStructures.Trees.BST.BinaryTree.Node<E>
     */
    @Override
    public BinaryTree.Node<E> insertValue(E value){
        Node<E> node = (Node<E>) super.insertValue(value);
        if (node != null) {
            rebalance(node);
        }
        return node;
    }

    /**
     * 重写删除节点
     * @param value
     * @return zhaoyu.DataStructures.Trees.BST.BinaryTree.Node<E>
     */
    @Override
    public BinaryTree.Node<E> deleteValue(E value){
        Node<E> node= (Node<E>) super.deleteValue(value);
        if (node == null) {
            return null;
        }
        Node<E> parentNode = (Node<E>) node.getParent();
        rebalance(parentNode);
        return node;
    }
}