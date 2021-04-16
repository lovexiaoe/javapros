package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Stacks;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 数组列表实现栈
 * @Author: zhaoyu
 * @Date: 2021/1/21
 */
public class StackArrayList<T> {

    private List<T> list;

    public StackArrayList() {
        list=new ArrayList();
    }

    public void push(T element){
        list.add(element);
    }

    public T pop(){
        if (this.isEmpty()) {
            throw new RuntimeException("栈已经为空");
        }
        return list.remove(size()-1);
    }

    public T peek(){
        if (this.isEmpty()) {
            throw new RuntimeException("栈已经为空");
        }
        return list.get(size()-1);
    }

    public void print(){
        for (int i = 0; i < size(); i++) {
            System.out.print(list.get(i));
        }
        System.out.println();
    }

    public int size(){
        return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public static void main(String[] args) {
        StackArrayList<Integer> stack = new StackArrayList();

        for (int i = 1; i <= 5; ++i) {
            stack.push(i);
            assert stack.size() == i;
        }

        assert stack.size() == 5;
        assert stack.peek() == 5 && stack.pop() == 5 && stack.peek() == 4;

        stack.print();

        /* pop elements at the top of this stack one by one */
        while (!stack.isEmpty()) {
            stack.pop();
        }
        assert stack.isEmpty();

        try {
            stack.pop();
            assert false; /* this should not happen */
        } catch (RuntimeException e) {
            assert true; /* this should happen */
        }
    }
}
