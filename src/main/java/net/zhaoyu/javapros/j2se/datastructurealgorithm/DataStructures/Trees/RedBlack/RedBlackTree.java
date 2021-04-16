package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.RedBlack;

import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinarySearchTree;
import net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST.BinaryTree;

/**
 * @Description: 代码实际运行可能有问题，需要优化
 * @Author: zhaoyu
 * @Date: 2021/3/8
 */
public class RedBlackTree<E extends Comparable<E>> extends BinarySearchTree<E>{
    /**
     * 红黑树使用的节点，包含了黑节点高度和节点颜色
     * @author: zhaoyu
     * @date: 2021/3/9
     */
    public static class Node<E> extends BinaryTree.Node<E>{
        protected int blackHeight=0;
        protected  boolean black=false;
        public Node(BinaryTree.Node<E> parent, BinaryTree<E> containerTree, E value) {
            super(parent, containerTree, value);
        }
    }

    @Override
    protected BinaryTree.Node<E> newNode(BinaryTree.Node<E> parent,BinaryTree<E> containerTree,E value){
        return new Node<>(parent, containerTree, value);
    }

    /**
     * 节点是否为黑色，null返回为黑色。
     * @param node
     * @return boolean
     */
    protected boolean nullSafeBlack(Node<E> node){
        if (node == null) {
            return true;
        } else {
            return node.black;
        }
    }

    /**
     * 插入节点后，对节点进行调整，参考md中的插入。
     * @param node
     * @return void
     */
    protected void rebalanceForInsert(Node<E> node){
        if (node.getParent() == null) {
            node.black = true;
        } else {
            Node<E> parent=(Node<E>) node.getParent();
            if (parent.black) {
                //case1
                return;
            } else {
                Node<E> grandParent= (Node<E>) parent.getParent();
                //父节点在祖父节点中的左右方向。
                boolean nodeLeftGrandChild=grandParent.getLeft()==parent;
                Node<E> uncle= (Node<E>) (nodeLeftGrandChild?grandParent.getRight():grandParent.getLeft());
                //case2
                if (!nullSafeBlack(uncle)) {
                    if (grandParent != root) {
                        grandParent.black = false;
                    }
                    uncle.black = true;
                    parent.black = true;
                    rebalanceForInsert(grandParent);
                } else {
                    //节点在父节点中的左右方向。
                    boolean nodeLeftParent=nodeLeftGrandChild?parent.getRight()==node:parent.getLeft()==node;

                    //case4
                    if (nodeLeftParent) {
                        rotate(parent, nodeLeftGrandChild);
                        node=parent;
                        parent= (Node<E>) node.getParent();
                    }

                    //case3
                    parent.black=true;
                    grandParent.black=false;
                    rotate(grandParent,!nodeLeftGrandChild);
                }
            }
        }
    }

    /**
     * 插入节点
     * @param value
     * @return zhaoyu.DataStructures.Trees.BST.BinaryTree.Node<E>
     */
    @Override
    public BinaryTree.Node<E> insertValue(E value){
        Node<E> node = (Node<E>) super.insertValue(value);
        if (node != null) {
            rebalanceForInsert(node);
        }
        return node;
    }

    /**
     * 删除后的重新平衡，参考md
     * @param parent
     * @param nodeDirectionLeft
     * @return void
     */
    protected void rebalanceForDelete(Node<E> parent,boolean nodeDirectionLeft){
        if (parent == null) {
            return;
        }
        //当前节点
        Node<E> node = (Node<E>) (nodeDirectionLeft ? parent.getLeft() : parent.getRight());
        //操作的节点一定是黑色的。
        if (!nullSafeBlack(node)) {
            node.black=true;
            return;
        }
        //兄弟节点
        Node<E> sibling = (Node<E>) (nodeDirectionLeft? parent.getRight(): parent.getLeft());
        //近子侄几点
        Node<E> nearNephew = (Node<E>) (nodeDirectionLeft? sibling.getLeft():sibling.getRight());
        //远子侄节点
        Node<E> awayNephew = (Node<E>) (nodeDirectionLeft? sibling.getRight():sibling.getLeft());
        if(parent.black){
            if(sibling.black){
                //case1
                if(nullSafeBlack(nearNephew) && nullSafeBlack(awayNephew)){
                    sibling.black = false;
                    if(parent.getParent()!=null){
                        rebalanceForDelete (
                                (Node<E>) parent.getParent(),
                                parent.getParent().getLeft() == parent);
                    }
                //case2
                }else if(!nullSafeBlack(awayNephew)){
                    awayNephew.black = true;
                    rotate(parent, nodeDirectionLeft);
                //case3
                }else{
                    nearNephew.black = true;
                    rotate(sibling, !nodeDirectionLeft);
                    rotate(parent, nodeDirectionLeft);
                }
            //case4
            }else{
                parent.black = false;
                sibling.black = true;
                rotate(parent, nodeDirectionLeft);
                rebalanceForDelete(parent, nodeDirectionLeft);
            }
        }else{
            //case5
            if(nullSafeBlack(nearNephew)){
                rotate(parent, nodeDirectionLeft);
            //case6
            }else{
                parent.black = true;
                rotate(sibling, !nodeDirectionLeft);
                rotate(parent, nodeDirectionLeft);
            }
        }

    }

    @Override
    public BinaryTree.Node<E> deleteValue(E value){
        Node<E> deleted = (Node<E>) super.deleteValue(value);

        if (deleted != null && deleted.black && deleted.getParent() != null) {
            Node<E> deletedNodeChild = (Node<E>) (deleted.getLeft() == null ? deleted.getRight() : deleted.getLeft());
            boolean isLeftChild;
            if (deletedNodeChild != null) {
                //删除节点无子节点或者有一个子节点的情况，参照BST的删除方法。
                isLeftChild = deletedNodeChild.getParent().getLeft() == deletedNodeChild;
            } else {
                //删除节点有两个子节点的情况，参照BST的删除方法。
                isLeftChild = deleted.getParent().getRight() != null;
            }
            rebalanceForDelete((Node<E>) deleted.getParent(), isLeftChild);
        }
        return deleted;
    }
}
