package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.DynamicArray;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @Description: 动态数组
 * @Author: zhaoyu
 * @Date: 2020/12/18
 */
public class DynamicArray<E> implements Iterable<E>{
    /**
     * 存放数据的数组
     */
    private Object[] elements;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 数据大小
     */
    private Integer size;

    public DynamicArray() {
        this.size = 0;
        this.capacity = 10;
        this.elements = new Object[capacity];
    }

    public DynamicArray(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.elements = new Object[capacity];
    }

    /**
     * 增加
     */
    public void add(E obj){
        //判断大小是否足够，如果不足，扩展，修改容量。
        if (size>=this.elements.length) {
            extend();
        }
        //加入新元素
        elements[size++]=obj;
    }

    /**
     * 扩展数组，新建一个数组，大小为原来的两倍。复制原有数组到新的数组。
     */
    private void extend(){
        this.capacity *= 2;
        this.elements=Arrays.copyOf(this.elements, capacity);
    }

    private E getElement(int index){
        return (E) this.elements[index];
    }

    private void fastRemove(int index){
        int newSize = this.size - 1;
        if (index < newSize) {
            System.arraycopy(elements,index+1,elements,index,newSize-index);
        }
        elements[this.size=newSize]=null;
    }

    public void put(E obj,int index){
        this.elements[index]=obj;
    }

    public E get(int index){
        return getElement(index);
    }

    public E remove(int index){
        E oldElement = getElement(index);
        fastRemove(index);
        return oldElement;
    }

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size==0;
    }

    public Stream<E> stream(){
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public String toString(){
        return Arrays.toString(Arrays.stream(elements).filter(Objects::nonNull).toArray());
    }

    @Override
    public Iterator<E> iterator() {
        return new DynamicArrayIterator();
    }

    private class DynamicArrayIterator implements Iterator<E>{
        private int index;

        @Override
        public boolean hasNext() {
            return index<size;
        }

        @Override
        public E next() {
            if (index >= size) {
                throw new NoSuchElementException();
            }
            if (index > elements.length) {
                throw new ConcurrentModificationException();
            }
            return getElement(index++);
        }

        @Override
        public void remove() {
            if (this.index < 0) {
                throw new IllegalStateException();
            }
            DynamicArray.this.remove(index--);
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            for (int i = 0; i < size; i++) {
                action.accept(getElement(i));
            }
        }
    }

    public static void main(String[] args) {
        DynamicArray<String> names = new DynamicArray<>();
        names.add("Peubes");
        names.add("Marley");

        System.out.println("iterator=====");
        Iterator itr=names.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println("foreach=====");
        names.stream().forEach(System.out::println);

        System.out.println(names);

        System.out.println(names.size());

        names.remove(0);

        for (String name : names) {
            System.out.println(name);
        }
    }
}
