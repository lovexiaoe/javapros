package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Heaps;

/**
 * @Description: 最小优先级队列，最小优先级队列是堆的一种
 * @Author: zhaoyu
 * @Date: 2020/12/29
 */
public class MinPriorityQueue {
    private int[] heap;
    private int capacity;
    private int size;

    public MinPriorityQueue(int capacity) {
        this.heap = new int[capacity+1];
        this.capacity = capacity;
        this.size = 0;
    }

    public void insert(int key){
        if (isFull()) {
            return;
        }
        int k = this.size + 1;
        this.heap[k]=key;
        //堆结构toggleUp。
        while (k > 1) {
            if (heap[k] < heap[k / 2]) {
                swap(k,k/2);
            }
            k=k/2;
        }
        this.size++;
    }

    public int peek() {
        return this.heap[1];
    }

    private void swap(int i1,int i2){
        int temp = heap[i1];
        heap[i1] = heap[i2];
        heap[i2] = temp;
    }

    public boolean isEmpty(){
        return 0==this.size;
    }

    public boolean isFull(){
        return this.capacity == this.size;
    }

    public void print() {
        for (int i = 1; i <= this.capacity; i++) {
            System.out.print(this.heap[i] + " ");
        }
        System.out.println();
    }

    /**
     * 将首节点做toggleDown操作。
     * @param
     * @return void
     */
    private void sink(){
        int k=1;
        while (2*k<=this.size) {
            int minIndex;
            //子节点都比当前节点大，不需要做heap的toggleDown操作
            if (this.heap[2 * k] >= this.heap[k]) {
                if (2 * k + 1 <= this.size && this.heap[2 * k + 1] >= this.heap[k]) {
                    break;
                } else if (2 * k + 1 > this.size) {
                    break;
                }
            }

            // 子节点有比当前节点小的，取当前节点和子节点中最小节点的index。
            if (2 * k + 1 > this.size) {
                minIndex = this.heap[2 * k] < this.heap[k] ? 2 * k : k;
            } else {
                if (this.heap[k] > this.heap[2 * k] || this.heap[k] > this.heap[2 * k + 1]) {
                    minIndex = this.heap[2 * k] < this.heap[2 * k + 1] ? 2 * k : 2 * k + 1;
                } else {
                    minIndex = k;
                }
            }

            // 节点互换
            swap(k, minIndex);
            k = minIndex;
        }
    }

    /**
     * 使用删除的方法，排序堆的元素
     * @param
     * @return void
     */
    public void heapSort() {
        for (int i = 1; i < this.capacity; i++) {
            this.delete();
        }
    }

    /**
     * 删除首节点，并重新平衡heap，将删除的节点放到最后。所以删除的节点是按照从小到大排序的。
     * @return
     */
    public int delete() {
        int min = this.heap[1];
        this.heap[1] = this.heap[this.size];
        this.heap[this.size] = min;
        this.size--;
        this.sink();
        return min;
    }

    public static void main(String[] args) {
        // testing
        MinPriorityQueue q = new MinPriorityQueue(8);
        q.insert(5);
        q.insert(2);
        q.insert(4);
        q.insert(1);
        q.insert(7);
        q.insert(6);
        q.insert(3);
        q.insert(8);
        q.print(); // [ 1, 2, 3, 5, 7, 6, 4, 8 ]
        q.heapSort();
        q.print(); // [ 8, 7, 6, 5, 4, 3, 2, 1 ]
    }
}
