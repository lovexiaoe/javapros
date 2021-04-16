package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Bags;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @Description: 模仿一个口袋， 不允许删除元素的集合（只允许添加和遍历）
 * @Author: zhaoyu
 * @Date: 2020/12/16
 */
public class Bag<Element> implements Iterable<Element> {

    private Node<Element> first;
    private int size;

    static class Node<Element>{
        private Element content;
        private Node<Element> next;
    }

    public Bag() {
        this.first = null;
        this.size = 0;
    }

    public boolean isEmpty(){
        return first==null;
    }

    public int size(){
        return size;
    }

    /**
     * 口袋放入东西时，新的元素为第一个元素，将旧的元素挤下去。 允许重复
     * @param element
     */
    public void add(Element element) {
        Node<Element> oldFirst=first;
        first = new Node<>();
        first.content=element;
        first.next=oldFirst;
        size++;
    }

    public boolean contains(Element element){
        Iterator iterator=this.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * iterable接口方法实现。
     * @return
     */
    @Override
    public Iterator<Element> iterator() {
        return new BagIterator(first);
    }

    /**
     * Iterator定义
     */
    private class BagIterator implements Iterator<Element>{

        private Node<Element> current;

        public BagIterator(Node<Element> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public Element next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Element e= current.content;
            current = current.next;
            return e;
        }
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();
        bag.add("1");
        bag.add("2");
        bag.add("2");
        bag.add("3");

        System.out.println("size of bag = " + bag.size());
        for (String s : bag) {
            System.out.println(s);
        }

        System.out.println(bag.contains(null));
        System.out.println(bag.contains("1"));
        System.out.println(bag.contains("4"));

    }
}
