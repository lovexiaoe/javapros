package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Stacks;

/**
 * 栈是一种先入后出的队列，下面使用节点实现栈。
 * @Author: zhaoyu
 * @Date: 2020/12/31
 */
public class NodeStack <T>{

    public static void main(String[] args) {
        NodeStack<Integer> Stack = new NodeStack<>();

        Stack.push(3);
        Stack.push(4);
        Stack.push(5);
        System.out.println("Testing :");
        Stack.print(); // prints : 5 4 3

        Integer x = Stack.pop(); // x = 5
        Stack.push(1);
        Stack.push(8);
        Integer y = Stack.peek(); // y = 8
        System.out.println("Testing :");
        Stack.print(); // prints : 8 1 4 3

        System.out.println("Testing :");
        System.out.println("x : " + x);
        System.out.println("y : " + y);
    }

    private Node head;
    private int size;

    public NodeStack(Node head,int size) {
        this.head = head;
    }

    public NodeStack(){
       this(null,0);
    }

    class Node<T>{
        T element;
        Node next;

        public Node(T element) {
            this.element = element;
        }
    }

    public void push(T element){
        Node current = new Node(element);
        current.next=head;
        head=current;
        size++;
    }

    public T pop(){
        if (size <1) {
            throw new NullPointerException("栈已经为空");
        }
        Node oldHead=head;
        head = head.next;
        size--;
        return (T) oldHead.element;
    }

    public T peek() {
        return (T) head.element;
    }

    public void print(){
        Node current=head;
        while (current != null) {
            System.out.print(current.element);
            current=current.next;
        }
        System.out.println();
    }
}
