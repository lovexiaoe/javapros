package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Queues;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 使用ArrayList实现的FIFO队列
 * @Author: zhaoyu
 * @Date: 2020/12/30
 */
public class GenericArrayListQueue<T> {
    List<T> queue = new ArrayList<>();

    private boolean hasElement(){
        return !queue.isEmpty();
    }

    /**
     * 读取顶部元素
     * @return
     */
    public T peek(){
        T result=null;
        if (!hasElement()) {
            result= queue.get(0);
        }
        return result;
    }

    /**
     * 弹出顶部元素
     * @param
     * @return T
     */
    public T poll(){
        T result=null;
        if (hasElement()) {
            result = queue.remove(0);
        }
        return result;
    }

    /**
     * 将元素添加到底部
     * @param element
     * @return void
     */
    public boolean add(T element){
        return queue.add(element);
    }

    public static void main(String[] args) {
        GenericArrayListQueue<Integer> queue = new GenericArrayListQueue<Integer>();
        System.out.println("Running...");
        assert queue.peek() == null;
        assert queue.poll() == null;
        assert queue.add(1) == true;
        assert queue.peek() == 1;
        assert queue.add(2) == true;
        assert queue.peek() == 1;
        assert queue.poll() == 1;
        assert queue.peek() == 2;
        assert queue.poll() == 2;
        assert queue.peek() == null;
        assert queue.poll() == null;
        System.out.println("Finished.");
    }
}
