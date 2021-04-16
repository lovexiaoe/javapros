package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements Iterable<E> {
    protected int length=0;
    protected Node<E> first;
    protected Node<E> last;

    public static class Node<E> {
        protected E value;
        protected Node<E> next;

        public String toString() {
            return value.toString();
        }

        public Node getNext() {
            return next;
        }

        public E getValue() {
            return value;
        }
    }

    public int getLength(){
        return length;
    }

    /**
     * 将一个list追加到当前list后
     * @param anotherList
     */
    public void appendAllLast(LinkedList<E> anotherList){
        if(first == null){
            first = anotherList.first;
            last = anotherList.last;
        }else{
            last.next = anotherList.first;
            last = anotherList.last;
        }
        length += anotherList.length;

    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    public class ListIterator implements Iterator<E>{

        protected Node<E> current=first;


        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            current = current.next;
            return current.next.value;
        }
    }

    protected Node<E> getNewNode(){
        Node<E> node=new Node<E>();
        return node;
    }

    public Node<E> appendFirst(E value){
        Node node = getNewNode();
        node.value = value;
        node.next = first;
        first = node;
        if (length == 0)
            last = node;
        length++;
        return node;
    }

    public Node<E> appendLast(E value) {
        Node node = getNewNode();
        node.value = value;
        if (last != null)
            last.next = node;
        last = node;
        if (first == null) {
            first = node;
        }

        length++;
        return node;
    }

    public Node<E> insert(int index,E value){
        Node<E> node = getNewNode();
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("参数错误：非法的序列");
        } else if (index == length) {
            appendLast(value);
        } else if (index == 0) {
            appendFirst(value);
        } else {
            Node<E> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            node.value = value;
            node.next=current.next;
            current.next = node;
            length++;
        }
        return node;
    }

    public E getFirst() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return first.value;
    }

    public E getLast() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return last.value;
    }

    public Node<E> removeFirst() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        Node<E> origFirst = first;
        first = first.next;
        length--;
        if (length == 0) {
            last = null;
        }
        return origFirst;
    }


    public E findAtIndex(int index) {
        Node<E> result = first;
        while (index >= 0) {
            if (result == null) {
                throw new NoSuchElementException();
            } else if (index == 0) {
                return result.value;
            } else {
                index--;
                result = result.next;
            }
        }
        return null;
    }

    protected Node<E> removeAtIndex(int index) {
        if (index >= length || index < 0) {
            throw new NoSuchElementException();
        }

        if (index == 0) {
            Node<E> nodeRemoved = first;
            removeFirst();
            return nodeRemoved;
        }

        Node justBeforeIt = first;
        while (--index > 0) {
            justBeforeIt = justBeforeIt.next;
        }

        Node<E> nodeRemoved = justBeforeIt.next;
        if (justBeforeIt.next == last) {
            last = justBeforeIt.next.next;
        }
        justBeforeIt.next = justBeforeIt.next.next;

        length--;

        return nodeRemoved;

    }

    public void setValueAtIndex(int index, E value){
        Node<E> result = first;
        while (index >= 0) {
            if (result == null) {
                throw new NoSuchElementException();
            } else if (index == 0) {
                result.value = value;
                return;
            } else {
                index--;
                result = result.next;
            }
        }
    }


    public Node<E> removeLast() {
        return removeAtIndex(length - 1);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Node<E> node = first;
        while(node!=null){
            if(node.value==null){
                sb.append("null");
            }else{
                sb.append(node.value.toString()+", ");
            }
            node=node.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
