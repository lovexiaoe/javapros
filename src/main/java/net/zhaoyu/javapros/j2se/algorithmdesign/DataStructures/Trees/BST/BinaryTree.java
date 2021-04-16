package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Trees.BST;

import java.util.Stack;
import java.util.function.Consumer;

/**
 * @Description: 二叉树，二叉树每个节点有两个子节点。左子节点通常小于父节点，父节点小于右节点。
 *  二叉树由于结构简单，所以得到广泛的应用。
 * @Author: zhaoyu
 * @Date: 2021/1/27
 */
public class BinaryTree <E>{
    public static class Node<E> {
        protected E value;
        protected Node<E> left;
        protected Node<E> right;
        protected Node<E> parent;
        protected BinaryTree<E> containerTree;

        public Node(Node<E> parent,BinaryTree<E> containerTree,E value) {
            this.value=value;
            this.parent=parent;
            this.containerTree=containerTree;
        }

        public E getValue(){
            return value;
        }

        public Node<E> getLeft(){
            return left;
        }

        public Node<E> getRight(){
            return right;
        }

        public Node<E> getParent(){ return parent;}

        public BinaryTree<E> getContainerTree() { return containerTree; }
    }

    protected  Node<E> newNode(Node<E> parent,
                               BinaryTree<E> containerTree, E value){
        return new Node<>(parent, containerTree, value);
    }

    protected Node<E> root;

    public void addRoot(E value){
        if(root==null){
            root = newNode(null, this,  value);
        }else{
            throw new IllegalStateException("Root already exists");
        }
    }

    public Node<E> getRoot(){
        return root;
    }

    public Node<E> addChild(Node<E> parent, E value, boolean left) {
        if (parent == null) {
            throw new NullPointerException("父节点为空");
        } else if (parent.containerTree != this) {
            throw new IllegalArgumentException("父节点不属于当前树");
        } else {
            Node<E> child = newNode(parent, this, value);
            if (left) {
                parent.left = child;
            } else {
                parent.right=child;
            }
            return child;
        }
    }

    public Node<E> addLeft(Node<E> parent,E value){
        return addChild(parent, value, true);
    }

    public Node<E> addRight(Node<E> parent,E value){
        return addChild(parent, value, false);
    }

    /**
     * 深度遍历的类型，深度遍历类型有，先序（父左右），中序（左父右），后续（左右父），可看到是以父节点在遍历中的顺序区分的。
     */


    /**
     * 深度遍历类型定义
     * @author: zhaoyu
     * @date: 2021/2/5
     */
    public static enum DepthFirstTraversalType{
        PREORDER,
        INORDER,
        POSTORDER
    }

    /**
     * 递归遍历树，基本思路，按照遍历类型对<左节点处理><右节点处理><父节点处理>进行排序。
     * @param consumer
     * @param current
     * @param tOrder
     */
    public void traverseDepthFirst(Consumer<E> consumer,Node<E> current,DepthFirstTraversalType tOrder){
        if (current == null) {
            return;
        }
        //使用if判断控制当前节点的处理顺序
        if(tOrder == DepthFirstTraversalType.PREORDER){
            consumer.accept(current.value);
        }
        traverseDepthFirst(consumer, current.left, tOrder);
        if(tOrder == DepthFirstTraversalType.INORDER){
            consumer.accept(current.value);
        }
        traverseDepthFirst(consumer, current.right, tOrder);
        if(tOrder == DepthFirstTraversalType.POSTORDER){
            consumer.accept(current.value);
        }
    }

    /**
     * 非递归深度先序遍历，先序遍历最简单，中序和后序遍历有一些复杂。
     */
    public void traversePreOrderNonRecursive(Consumer<E> consumer){
        Stack<Node<E>> stack = new Stack<>();
        stack.push(getRoot());
        while (!stack.isEmpty()) {
            Node<E> current=stack.pop();
            consumer.accept(current.getValue());
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
    }

    /**
     * 非递归深度中序遍历，中序遍历需要先处理子节点，只有处理了子节点后，才能处理父节点，我们用一个flag标记它的子节点是否被加入到栈中。
     * 这样，从根节点开始处理，每个节点会被加入到栈中两次，第一次是flag为false，处理一次后，变为true再次添加到队列中。队列在下次pop后
     * 检测到标记为true的，就会进行处理。
     */
    public void traverseInOrderNonRecursive(Consumer<E> consumer){
        /**
         * 给节点添加一个标记，作为栈中存储对象
         */
        class StackFame{
            Node<E> node;
            /**
             * 子节点是否被加入栈中的标记。
             */
            boolean childrenPushed=false;

            public StackFame(Node<E> node, boolean childrenPushed) {
                this.node = node;
                this.childrenPushed = childrenPushed;
            }
        }

        Stack<StackFame> stack=new Stack<>();
        stack.push(new StackFame(getRoot(), false));
        while (!stack.isEmpty()) {
            StackFame current=stack.pop();
            if (current.childrenPushed) {
                consumer.accept(current.node.getValue());
            } else {
                if (current.node.right != null) {
                    stack.push(new StackFame(current.node.right, false));
                }
                stack.push(new StackFame(current.node, true));
                if (current.node.left != null) {
                    stack.push(new StackFame(current.node.left, false));
                }
            }
        }
    }

    /**
     * 非递归后续遍历，和中序遍历类似
     * @param consumer
     */
    public void traversePostOrderNonRecursive(Consumer<E> consumer){
        /**
         * 给节点添加一个标记，作为栈中存储对象
         */
        class StackFame{
            Node<E> node;
            /**
             * 子节点是否被加入栈中的标记。
             */
            boolean childrenPushed=false;

            public StackFame(Node<E> node, boolean childrenPushed) {
                this.node = node;
                this.childrenPushed = childrenPushed;
            }
        }

        Stack<StackFame> stack=new Stack<>();
        stack.push(new StackFame(getRoot(), false));
        while (!stack.isEmpty()) {
            StackFame current=stack.pop();
            if (current.childrenPushed) {
                consumer.accept(current.node.getValue());
            } else {
                stack.push(new StackFame(current.node, true));
                if (current.node.right != null) {
                    stack.push(new StackFame(current.node.right, false));
                }
                if (current.node.left != null) {
                    stack.push(new StackFame(current.node.left, false));
                }
            }
        }
    }

    /**
     * 修改节点的值
     * @param node
     * @param value
     * @return void
     */
    public void setValue(Node<E> node,E value){
        if(node==null){
            throw new NullPointerException("操作节点为空。");
        } else if (node.containerTree != this) {
            throw new IllegalArgumentException("父节点不属于当前树");
        } else {
            node.value=value;
        }
    }

    /**
     * 二叉树的旋转操作。
     * @param node
     * @param left
     * @return void
     */
    protected void rotate(Node<E> node,boolean left){
        if (node == null) {
            throw new IllegalArgumentException("操作节点为空");
        } else if (node.containerTree != this) {
            throw new IllegalArgumentException("节点不属于当前树");
        }
        //用于作为根的子节点
        Node<E> child=null;
        //需要移动的中间孙子节点。
        Node<E> grandchild=null;
        //父节点
        Node<E> parent=node.getParent();
        //旋转节点在父节点中是左节点还是右节点。
        boolean parentDirection;

        //分左右初始化子节点和中间孙子节点。
        if (left) {
            child = node.getRight();
            if (child != null) {
                grandchild = child.getLeft();
            }
        } else {
            child=node.getLeft();
            if (child != null) {
                grandchild=child.getRight();
            }
        }
        if (node != getRoot()) {
            //判断node在父节点中的方向
            if (parent.getLeft() == node) {
                parentDirection = true;
            } else {
                parentDirection = false;
            }
            //从下到上删除旧关系。
            if (grandchild != null) {
                deleteNodeWithSubtree(grandchild);
            }
            if (child != null) {
                deleteNodeWithSubtree(child);
            }
            deleteNodeWithSubtree(node);
            //从上到下建立新关系
            if (child != null) {
                setChild(parent, child, parentDirection);
                setChild(child, node, left);
            }
            if (grandchild != null) {
                setChild(node, grandchild, !left);
            }
        } else {
            //从下到上删除旧关系。
            if (grandchild != null) {
                deleteNodeWithSubtree(grandchild);
            }
            if (child != null) {
                deleteNodeWithSubtree(child);
            }
            deleteNodeWithSubtree(node);
            //从上到下建立新关系
            if (child != null) {
                root=child;
                setChild(child, node, left);
            }
            if (grandchild != null) {
                setChild(node, grandchild, !left);
                root.parent=null;
            }
        }
    }

    /**
     * 删除没有子节点的节点。
     * @param node
     * @return void
     */
    public void deleteNodeWithSubtree(Node<E> node){
        if (node == null) {
            throw new NullPointerException("父节点为空，不能删除");
        } else if (node.containerTree != this) {
            throw new IllegalArgumentException("节点不属于当前树");
        } else {
            if (node == getRoot()) {
                root = null;
                return;
            } else {
                Node<E> parent=node.getParent();
                if (parent.getLeft() == node) {
                    parent.left = null;
                } else {
                    parent.right= null;
                }
            }
        }

    }

    /**
     * 用一个节点的子节点代替该节点，适用于节点只有左子树或者右子树的情况。
     * @param parent 节点的父节点
     * @param child 节点的子树节点。
     * @param left 节点是否是父节点的左节点。
     * @return zhaoyu.DataStructures.Trees.BST.BinaryTree.Node<E>
     */
    public Node<E> setChild(Node<E> parent,Node<E> child,boolean left){
        if (parent == null) {
            throw new NullPointerException("未找到父节点");
        } else if (parent.containerTree != this) {
            throw new IllegalArgumentException("父节点不是当前树节点");
        } else {
            if (left) {
                parent.left = child;
            } else {
                parent.right=child;
            }
            if (child != null) {
                child.parent=parent;
            }
            return child;
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.addRoot(1);
        Node n1 = tree.getRoot();
        Node<Integer> n2 = tree.addChild(n1, 2, true);
        Node<Integer> n3 = tree.addChild(n1, 3, false);
        Node<Integer> n4 = tree.addChild(n2, 4, true);
        Node<Integer> n5 = tree.addChild(n2, 5, false);
        Node<Integer> n6 = tree.addChild(n3, 6, true);
        Node<Integer> n7 = tree.addChild(n3, 7, false);
        Node<Integer> n8 = tree.addChild(n4, 8, true);
        Node<Integer> n9 = tree.addChild(n4, 9, false);
        Node<Integer> n10 = tree.addChild(n5, 10, true);

        tree.traverseDepthFirst(System.out::print, tree.getRoot(),
                DepthFirstTraversalType.PREORDER);
        System.out.println();

        tree.traverseDepthFirst(System.out::print, tree.getRoot(),
                DepthFirstTraversalType.INORDER);
        System.out.println();

        tree.traverseDepthFirst(System.out::print, tree.getRoot(),
                DepthFirstTraversalType.POSTORDER);
        System.out.println();
        System.out.println();

        tree.traversePreOrderNonRecursive(x-> System.out.print(""+x));
        System.out.println();
        tree.traverseInOrderNonRecursive(x-> System.out.print(""+x));
        System.out.println();
        tree.traversePostOrderNonRecursive(x-> System.out.print(x));
        System.out.println();
    }
}
