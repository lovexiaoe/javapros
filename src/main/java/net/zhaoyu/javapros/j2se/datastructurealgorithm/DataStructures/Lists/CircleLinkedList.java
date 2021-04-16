package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Lists;

/**
 * @Description: 循环链表
 * @Author: zhaoyu
 * @Date: 2020/12/21
 */
public class CircleLinkedList<E> {

    private static class Node<E>{
        /** 元素 */
        E value;
        /** 指针 */
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }
    }

    private Node<E> head;

    /**
     * 最后一个元素指针
     */
    private Node<E> tail;

    private int size;

    public CircleLinkedList() {
        this.head = new Node<>(null);
        head.next=head;
        this.tail=head;
        this.size = 0;
    }

    public int size(){
        return size;
    }

    public void append(E value){
        if (value == null) {
            throw new NullPointerException("不能添加为null的元素");
        }
        Node<E> current = new Node<>(value);
        tail.next = current;
        tail=current;
        tail.next=head;
        size++;
    }

    public E remove(int index){
        if (index<0||index>size){
            throw new IndexOutOfBoundsException("位置不能大于size或者小于0");
        }

        //查找删除节点的前一个节点
        Node<E> before = head;
        for (int i = 0; i < index; i++) {
            before=before.next;
        }

        //删除
        Node<E> destroy=before.next;
        E value= destroy.value;
        before.next=before.next.next;

        //清除节点
        destroy=null;

        size--;
        return value;
    }

    public void print(){
        Node<E> current=head;
        System.out.print("head->");
        while ((current=current.next)!=head) {
            System.out.print(current.value+"->");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        CircleLinkedList<String> clist = new CircleLinkedList();
        clist.append("1A");
        clist.append("2B");
        clist.append("3B");
        clist.print();
        clist.remove(1);
        clist.print();
    }

}