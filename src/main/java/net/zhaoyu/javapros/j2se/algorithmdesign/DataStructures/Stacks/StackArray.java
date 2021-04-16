package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Stacks;

import java.util.Arrays;

/**
 * @Description: 数组实现栈
 * @Author: zhaoyu
 * @Date: 2021/1/21
 */
public class StackArray {
    private static final int DEFAULT_CAPACITY = 10;

    private Integer[] stack;
    private int size;

    public StackArray() {
        this.stack =new Integer[DEFAULT_CAPACITY];
        this.size=0;
    }

    private boolean isArrayFull(){
        return size>=stack.length;
    }

    private void extendArray(){
       stack= Arrays.copyOf(stack, stack.length * 2);
    }

    public void push(Integer element){
        if (isArrayFull()) {
            extendArray();
        }
        stack[size]=element;
        size++;
    }

    public Integer pop(){
        if (size == 0) {
            throw new RuntimeException("栈已经位空。");
        }
        return stack[--size];
    }

    public Integer peek(){
        if (size == 0) {
            throw new RuntimeException("栈已经位空。");
        }
        return stack[size-1];
    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        // Declare a stack of maximum size 4
        StackArray myStackArray = new StackArray();

        // Populate the stack
        for (int i = 0; i < 21; i++) {
            myStackArray.push(i);
        }

        System.out.println(myStackArray.peek() == 20);
        System.out.println(myStackArray.pop() == 20);
        System.out.println(myStackArray.peek() == 19);
        System.out.println(myStackArray.size() == 20);
    }
}
