package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Trees.BST;

/**
 * 二叉搜索树
 * 在一个有序队列中，二分法查找扩展到所有元素，就会形成一个二叉搜索树。参考binary-search-tree.png。
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
    protected Node<E> searchValue(E value,Node<E> root){
        if (root == null) {
            return null;
        }
        int comp = root.getValue().compareTo(value);
        if (comp == 0) {
            return root;
        } else if (comp > 0) {
            return searchValue(value, root.getLeft());
        } else {
            return searchValue(value, root.getRight());
        }
    }

    /**
     * 查找值，不存在，返回null。
     * @param value
     * @return zhaoyu.DataStructures.Trees.BST.BinaryTree.Node<E>
     */
    public Node<E> searchValue(E value){
        if (getRoot() == null) {
            return null;
        } else {
            return searchValue(value, getRoot());
        }
    }

    /**
     * 插入一个元素，分为两步，首先找到插入的位置，插入的值大于当前节点，向右查找，插入的值小于当前节点，向左查找。直到找到空位置。
     * 第二部简单地插入元素。
     * 参考binary-search-tree-insert.png
     * @param value
     * @param node
     * @return
     */
    protected Node<E> insertValue(E value,Node<E> node){
        int comp = value.compareTo(node.getValue());
        Node<E> child;
        if (comp <= 0) {
            child = node.getLeft();
            if (null == child) {
                return addChild(node, value, true);
            } else {
                return insertValue(value, child);
            }
        } else if (comp > 0) {
            child = node.getRight();
            if (null == child) {
                return addChild(node, value, false);
            } else {
                return insertValue(value, child);
            }
        } else {
            return null;
        }
    }

    public Node<E> insertValue(E value){
        if (getRoot() == null) {
            addRoot(value);
            return getRoot();
        } else {
            return insertValue(value, getRoot());
        }
    }

    public Node<E> deleteValue(E value) {
        Node node = searchValue(value);
        if (node == null) {
            return null;
        }
        return deleteNode(node);
    }

    /**
     * 从树种删除任意节点。
     * @param delNode
     * @return zhaoyu.DataStructures.Trees.BST.BinaryTree.Node<E>
     */
    private Node<E> deleteNode(Node<E> delNode){
        boolean left;
        if (delNode.getParent() != null && delNode.getParent().getLeft() == delNode) {
            left = true;
        } else {
            left=false;
        }

        //1，节点没有子节点，直接删除。
        if (delNode.getLeft() == null && delNode.getRight() == null) {
            deleteNodeWithSubtree(delNode);
            return delNode;
        }
        //2,只有右子树
        else if (delNode.getLeft()==null) {
            //如果删除的是根节点
            if (delNode.getParent() == null) {
                root = delNode.getRight();
            } else {
                setChild(delNode.getParent(), delNode.getRight(), left);
            }
            return delNode;
        }
        //3,只有左子树
        else if (delNode.getRight() == null) {
            //如果删除的是根节点
            if (delNode.getParent() == null) {
                root = delNode.getLeft();
            } else {
                setChild(delNode.getParent(), delNode.getLeft(), left);
            }
            return delNode;
        }
        //4,同时有左右子树。使用右子树的最小值替换删除节点，并删除右子树最小值，右子树最小值是叶节点，所以可以直接删除。
        else {
            //找到右子树最小节点。
            Node<E> nodeToBeReplaced = getLeftMost(delNode.getRight());
            //替换删除节点
            setValue(delNode, nodeToBeReplaced.getValue());
            //直接删除右子树最小值。
            deleteNode(nodeToBeReplaced);
            return nodeToBeReplaced;
        }
    }

    /**
     * 查找一个子树的最左节点并返回。
     * @param node
     * @return zhaoyu.DataStructures.Trees.BST.BinaryTree.Node<E>
     */
    protected Node<E> getLeftMost(Node<E> node){
        if (node == null) {
            return null;
        } else if (node.getLeft() == null) {
            return node;
        } else {
            return getLeftMost(node.getLeft());
        }
    }

    /**
     * 向树种插入20个随机数，并打印。打印后你会发现，中序遍历的二叉搜索树是按顺序排列好的
     * @param
     * @return void
     */
    public static void insert1to20() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int i = 0; i < 20; i++) {
            int value= (int) (100*Math.random());
            tree.insertValue(value);
        }
        tree.traverseDepthFirst(x-> System.out.print(x+","),tree.getRoot(),DepthFirstTraversalType.INORDER);
        System.out.println();
    }

    public static void main(String[] args) {
        insert1to20();
    }
}
