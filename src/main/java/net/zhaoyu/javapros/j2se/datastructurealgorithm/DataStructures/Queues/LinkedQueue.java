package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Queues;

import java.util.NoSuchElementException;

/**
 * @Description: 用链表实现的队列
 * @Author: zhaoyu
 * @Date: 2020/12/30
 */
public class LinkedQueue {
    private static class Node{
        int data;
        Node next;

        public Node(){
            this(0, null);
        }

        public Node(int data) {
            this(data,null);
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node front;

    private Node rear;

    private int size;

    public LinkedQueue() {
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 入队列
     * @param data
     * @return boolean
     */
    public boolean enqueue(int data){
        Node node=new Node(data);
        if (isEmpty()) {
            rear = front = node;
        } else {
            rear.next = node;
            rear=node;
        }
        size++;
        return true;
    }

    /**
     * 出队列
     * @param
     * @return int
     */
    public int dequeue(){
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空");
        }
        Node temp=front;
        int retValue= temp.data;
        front=front.next;
        //释放对象方便GC
        temp=null;
        if (--size == 0) {
            rear=null;
        }
        return retValue;
    }

    /**
     * 返回队列头
     * @return
     */
    public int peekFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        return front.data;
    }

    /**
     * 返回队列尾
     */
    public int peekRear() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        return rear.data;
    }

    public int size() {
        return size;
    }

    /** Clear all nodes in queue */
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        Node cur = front;
        builder.append("[");
        while (cur != null) {
            builder.append(cur.data).append(", ");
            cur = cur.next;
        }
        builder.replace(builder.length() - 2, builder.length(), "]");
        return builder.toString();
    }

    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();
        assert queue.isEmpty();

        queue.enqueue(1); /* 1 */
        queue.enqueue(2); /* 1 2 */
        queue.enqueue(3); /* 1 2 3 */
        System.out.println(queue); /* [1, 2, 3] */

        //assert需要通过-eaVM参数开启，否则不会生效。
        assert queue.size() == 3;
        System.out.println( queue.dequeue() == 1);
        assert queue.peekFront() == 2;
        assert queue.peekRear() == 3;
        System.out.println(queue);

        queue.clear();
        assert queue.isEmpty();

        System.out.println(queue); /* [] */
    }
}
