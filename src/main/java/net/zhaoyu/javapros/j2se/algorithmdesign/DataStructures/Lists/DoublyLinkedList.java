package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Lists;

/**
 * @Description: 双向链表,可以向两端进行扩展
 * @Author: zhaoyu
 * @Date: 2020/12/25
 */
public class DoublyLinkedList<E> {

    private Node<E> head;

    private Node<E> tail;

    private int size;

    public class Node<E>{
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }

    public DoublyLinkedList() {
        this.head = null;
        this.tail=null;
        this.size=0;
    }

    public DoublyLinkedList (E[] array){
        if (array == null) {
            throw new NullPointerException();
        }
        for (E element: array) {
            insertTail(element);
        }
        size = array.length;
    }

    /**
     * @Description: 头部插入元素
     * @Author: zhaoyu
     * @Date: 2020/12/26
     * @Param: element
     * @Return:
     */
    public void insertHead(E element){
        Node<E> node = new Node<>(element);
        if (isEmpty()) {
            head = tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }
        size++;
    }

    /**
     * 尾部插入元素
     * @param element
     */
    public void insertTail(E element){
        Node node = new Node(element);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    /**
     * @Description: 在任意位置插入元素
     * @Author: zhaoyu
     * @Date: 2020/12/26
     * @Param: element,index
     * @Return:
     */
    public void insertElementByIndex(E element,int index){
        if (index< 0||index>size) {
            throw new IndexOutOfBoundsException("数组越界，index必须为正整数，并小于等于"+size);
        }
        if (index == 0) {
            insertHead(element);
        } else {
            Node<E> prev=head;
            while (--index>0) {
                prev = head.next;
            }
            Node<E> node = new Node<>(element);
            prev.next.prev = node;
            node.next = prev.next;
            node.prev = prev;
            prev.next=node;
        }
        size++;
    }

    /**
     * @Description: 删除头部元素
     * @Author: zhaoyu
     * @Date: 2020/12/26
     * @Param: null
     * @Return:
     */
    public Node<E> deleteHead(){
        if (isEmpty()) {
            return null;
        }
        Node<E> temp=head;
        head = head.next;

        if (head == null) {
            //删除最后一个节点后，重置tail
            tail=null;
        } else {
            head.prev = null;
        }
        //scrubbing
        temp.next=null;
        size--;

        return temp;
    }

    /**
     * @Description: 删除尾部元素
     * @Author: zhaoyu
     */
    public Node<E> deleteTail(){
        if (isEmpty()) {
            return null;
        }
        Node<E> temp = tail;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next=null;
        }
        temp.prev=null;
        size--;
        return temp;
    }

    /**
     * 删除特定元素
     * @param element
     * @return
     */
    public Node<E> delete(E element){
        Node<E> current = head;
        while (!current.element.equals(element)) {
            if (current == tail) {
                throw new RuntimeException("删除的元素不存在");
            } else {
                current = current.next;
            }
        }

        if (current == head) {
            deleteHead();
        } else if (current == tail) {
            deleteTail();
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current.next=null;
            current.prev=null;
        }
        size--;
        return current;
    }

    /**
     * 清除列表
     */
    public void clearList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * 显示列表
     */
    public void display() { // Prints contents of the list
        Node current = head;
        while (current != null) {
            System.out.print(current.element.toString() + " ");;
            current = current.next;
        }
        System.out.println();
    }

    public boolean isEmpty(){
        return size==0;
    }

    public static void main(String args[]) {
        DoublyLinkedList myList = new DoublyLinkedList();
        myList.insertHead(13);
        myList.insertHead(7);
        myList.insertHead(10);
        myList.display(); // <-- 10(head) <--> 7 <--> 13(tail) -->

        myList.insertTail(11);
        myList.display(); // <-- 10(head) <--> 7 <--> 13 <--> 11(tail) -->

        myList.deleteTail();
        myList.display(); // <-- 10(head) <--> 7 <--> 13(tail) -->

        myList.delete(7);
        myList.display(); // <-- 10(head) <--> 13(tail) -->

        myList.insertElementByIndex(5, 1);
        myList.display(); // <-- 3(head) <--> 5 <-->  13 (tail) -->
        myList.clearList();
        myList.display();
        myList.insertHead(20);
        myList.display();
    }
}
