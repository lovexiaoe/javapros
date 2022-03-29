//package net.zhaoyu.javapros.test;
//
//
//import java.io.IOException;
//import java.util.*;
//
////用于自己临时调试一些程序。
//public class Test {
//    public static void main(String[] args) throws IOException {
//        int[][] inputArr={{1,1,1},{1,2,2},{1,3,2},{2,1},{1,4,4},{2,2}};
//        int k=3;
//        System.out.println(LRU(inputArr,k));
//    }
//
//    public int[] LRU(int[][] operators, int k){
//        List<Integer> result=new ArrayList<>();
//        int count=0;
//        Map<Integer, Entry> map1 = new HashMap<>();
//        int[] data = new int[k];
//        Node first=null;
//        Node tail=null;
//        int nodeCount=0;
//        for (int i = 0; i < operators.length; i++) {
//            if (operators[i][0] == 1) {//set逻辑
//                //在链表尾部追加key
//                Node node=new Node(operators[i][1]);
//                if (i == 0) {
//                    first = node;
//                } else {
//                    tail.next=node;
//                    node.prev=tail;
//                }
//                tail=node;
//
//
//                // 如果map中存在key，则更新value和Node引用。
//                if (map1.containsKey(operators[i][1])) {
//                    //从链表中删除原来节点
//                    map1.get(operators[i][1]).node.removeFromLink();
//                }
//                //则更新value和Node引用。
//                Entry entry = new Entry(operators[i][2], node);
//                map1.put(operators[i][1], entry);
//
//                if (nodeCount > k) {
//                    //删除头结点
//                }
//                nodeCount++;
//            } else if (operators[i][0] == 2) { //get逻辑
//                if (if (map1.containsKey(operators[i][1])) {
//
//                })
//                result.add(map.get(operators[i][1]));
//            }
//        }
//
//
//        return result;
//    }
//
//    class DoubleLinkedList{
//        Node first;
//        Node tail;
//
//        void removeFirst(){
//            if (first == null) {
//                return;
//            }
//            if (first.next != null) {
//                first = first.next;
//                first.prev.next = null;
//                first.prev = null;
//            } else {
//                first= tail =null;
//            }
//        }
//        void appendLast(Node node){
//            if (null == tail) {//表未初始化
//                first = node;
//            } else {
//                tail.next=node;
//                node.prev= tail;
//            }
//            tail=node;
//        }
//
//        void removeFromLink(Node node){
//            if (first == node && tail == node) {
//                first=tail=null;
//                return;
//            }
//            if (node.prev != null) {
//                node.prev.next=node.next;
//            }
//            if (node.next != null) {
//                node.next.prev=node.prev;
//            }
//            node.prev=null;
//            node.next=null;
//        }
//    }
//    class Node{
//        int value;
//        Node next,prev;
//
//        public Node(int value) {
//            this.value = value;
//            this.next = null;
//            this.prev = null;
//        }
//        public Node(int value, Node next) {
//            this.value = value;
//            this.next = next;
//        }
//
//        Node removeFromLink(){
//            if (this.prev != null) {
//                this.prev.next=this.next;
//            }
//            if (this.next != null) {
//                this.next.prev=this.prev;
//            }
//            this.prev=null;
//            this.next=null;
//            return this;
//        }
//    }
//
//    class Entry{
//        int value;
//        Node node;
//
//        public Entry(int value, Node node) {
//            this.value = value;
//            this.node = node;
//        }
//    }
//}
//
