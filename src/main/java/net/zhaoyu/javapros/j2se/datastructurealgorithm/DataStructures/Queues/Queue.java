package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Queues;

/**
 * 队列的实现。一个先入先出的结构,可以在尾部添加，可以在头部删除。
 * @Author: zhaoyu
 * @Date: 2020/12/30
 */
public class Queue {
    public static final int DEFAULT_CAPACITY=10;

    private int maxSize;
    private int [] queueArray;
    private int front;
    private int rear;
    private int nItems;

    public Queue(int maxSize) {
        this.maxSize = maxSize;
        this.queueArray = new int[maxSize];
        this.front=0;
        this.rear=-1;
        this.nItems=0;
    }

    public Queue(){
        this(DEFAULT_CAPACITY);
    }

    public boolean insert(int item){
        if (isFull()) {
            return false;
        }
        rear=(rear+1)%maxSize;
        queueArray[rear]=item;
        nItems++;
        return true;
    }

    public int remove(){
        if (isEmpty()) {
            return -1;
        }
        int temp = queueArray[front];
        front=(front+1)%maxSize;
        nItems--;
        return temp;
    }

    public boolean isEmpty(){
        return nItems==0;
    }

    public boolean isFull(){
        return nItems==maxSize;
    }

    public int peekFront() {
        return queueArray[front];
    }

    public int peekRear() {
        return queueArray[rear];
    }

    public int getSize() {
        return nItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = front; ; i = ++i % maxSize) {
            sb.append(queueArray[i]).append(", ");
            if (i == rear) {
                break;
            }
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Queue myQueue = new Queue(4);
        myQueue.insert(10);
        myQueue.insert(2);
        myQueue.insert(5);
        myQueue.insert(3);
        // [10(front), 2, 5, 3(rear)]

        System.out.println(myQueue.isFull());

        // [10, 2(front), 5, 3(rear)]
        myQueue.remove();

        // [7(rear), 2(front), 5, 3]
        myQueue.insert(7);

        // Will print 2
        System.out.println(myQueue.peekFront());
        // Will print 7
        System.out.println(myQueue.peekRear());
        // Will print [2, 5, 3, 7]
        System.out.println(myQueue.toString());
    }
}

