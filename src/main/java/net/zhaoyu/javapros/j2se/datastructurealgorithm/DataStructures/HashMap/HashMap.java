package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.HashMap;

/**
 * @Description: HashMap的简单实现
 * @Author: zhaoyu
 * @Date: 2020/12/28
 */
public class HashMap {
    private int size;
    private LinkedList[] buckets;

    public HashMap(int size) {
        this.buckets = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new LinkedList();
        }
        this.size = size;
    }

    /**
     * 简单的hash算法，通过key对size取余
     * @param key
     * @return int
     */
    public int hashing(int key){
        int hash=key%size;

        //处理key为负数的情况
        if (hash < 0) {
            hash+=size;
        }
        return hash;

    }

    public void insert(int key){
        int hash = hashing(key);
        buckets[hash].insert(key);
    }

    public void delete(int key) {
        int hash = hashing(key);
        buckets[hash].delete(key);
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.printf("Bucket %d :", i);
            System.out.println(buckets[i].display());
        }
    }

    /**
     * 单链表。
     */
    public static class LinkedList {
        private Node first;
        public LinkedList() {
            first = null;
        }

        public void insert(int key) {
            if (isEmpty()) {
                first = new Node(key);
                return;
            }

            Node temp = findEnd(first);
            temp.setNext(new Node(key));
        }

        private Node findEnd(Node n) {
            if (n.getNext() == null) {
                return n;
            } else {
                return findEnd(n.getNext());
            }
        }

        public Node findKey(int key) {
            if (!isEmpty()) {
                return findKey(first, key);
            } else {
                System.out.println("List is empty");
                return null;
            }
        }

        private Node findKey(Node n, int key) {
            if (n.getKey() == key) {
                return n;
            } else if (n.getNext() == null) {
                System.out.println("Key not found");
                return null;
            } else {
                return findKey(n.getNext(), key);
            }
        }

        public void delete(int key) {
            if (!isEmpty()) {
                if (first.getKey() == key) {
                    first = null;
                } else {
                    delete(first, key);
                }
            } else {
                System.out.println("List is empty");
            }
        }

        private void delete(Node n, int key) {
            if (n.getNext().getKey() == key) {
                if (n.getNext().getNext() == null) {
                    n.setNext(null);
                } else {
                    n.setNext(n.getNext().getNext());
                }
            }
        }

        public String display() {
            return display(first);
        }

        private String display(Node n) {
            if (n == null) {
                return "null";
            } else {
                return n.getKey() + "->" + display(n.getNext());
            }
        }

        public boolean isEmpty() {
            return first == null;
        }
    }

    /**
     * 单链表节点
     */
    public static class Node {
        private Node next;
        private int key;

        public Node(int key) {
            next = null;
            this.key = key;
        }

        public Node getNext() {
            return next;
        }

        public int getKey() {
            return key;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
