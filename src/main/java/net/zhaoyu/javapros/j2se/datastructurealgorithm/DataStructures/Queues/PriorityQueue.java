package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Queues;

/**
 * 一个优先级队列按照元素的优先级将元素插入到对于的位置中。所以最重要的元素被放在顶部。
 * 在本例中越大的数据，他们的优先级越高。队列通常不会定义不定大小，但是当使用数组实现时，需要给出一个大小
 * @Author: zhaoyu
 * @Date: 2020/12/30
 */
public class PriorityQueue {
    private int maxSize;
    private int[] queueArray;
    private int nItems;

    public PriorityQueue(int size) {
        this.maxSize = size;
        queueArray = new int[size];
        nItems=0;
    }

    /**
     * 根据大小判断位置，插入到有序的列表中，插入点后面的元素后移。
     * @param item
     * @return void
     */
    public void insert(int item){
        if (isFull()) {
            throw new RuntimeException("queue is full");
        }
        int position=nItems-1;
        while (position >=0 && queueArray[position] > item) {
            queueArray[position+1]=queueArray[position];
            position--;
        }

        queueArray[position+1]=item;
        nItems++;
    }

    public int poll() {
        return queueArray[--nItems];
    }

    public int peek() {
        return queueArray[nItems - 1];
    }

    public boolean isEmpty() {
        return (nItems == 0);
    }

    public boolean isFull() {
        return (nItems == maxSize);
    }

    public int getSize() {
        return nItems;
    }

    public static void main(String[] args) {
        PriorityQueue myQueue = new PriorityQueue(4);
        myQueue.insert(10);
        myQueue.insert(2);
        myQueue.insert(5);
        myQueue.insert(3);
        // [2, 3, 5, 10] Here higher numbers have higher priority, so they are on the top

        for (int i = 3; i >= 0; i--) {
            // will print the queue in reverse order [10, 5, 3, 2]
            System.out.print(myQueue.poll() + " ");
        }
        // 让你所见，优先级队列可以用户排序。
    }
}
