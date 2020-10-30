package net.zhaoyu.javapros.j2se.functionalprogramming.functionallinkedlist;

/**
 * 创建不可变list，创建一个不可变list，可以将它分为两个部分。
 * 1，head:包含了list的第一个元素。
 * 2，tail:是一个包含了剩余元素的另一个list。
 *
 * 这样我们就递归地定义了一个list。链表的递归表示如下：
 * 1，是一个空列表。
 * 2，或者由如下两部分组成
 *    - head:包含了list的第一个元素。
 *    - tail:是一个包含了剩余元素的另一个list。
 */
public class LinkedList<E> {
    private E head;
    private LinkedList<E> tail;
    private LinkedList(){
    }
    private LinkedList(E head, LinkedList<E> tail){
        this.head = head;
        this.tail = tail;
    }
    public E head(){
        return head;
    }
    public LinkedList<E> tail(){
        return tail;
    }
    //添加元素
    public LinkedList<E> add(E value){
        return new LinkedList<E>(value,this);
    }

    public static final class EmptyList<E> extends LinkedList<E> {
        @Override
        public E head() {
            throw new IllegalStateException("head() invoked on empty list");
        }
        @Override
        public LinkedList<E> tail() {
            throw new IllegalStateException("tail() invoked on empty list");
        }
        @Override
        public void forEach(OneArgumentStatement<E> processor) {}

        @Override
        public <R> LinkedList<R> map(OneArgumentExpression<E, R> transformer) {
            return LinkedList.emptyList();
        }

        @Override
        public <R> R foldLeft(R initialValue, TwoArgumentExpression<R, E, R> computer) {
            return initialValue;
        }

        @Override
        public LinkedList<E> filter(OneArgumentExpression<E, Boolean> selector) {
            return this;
        }
    }
    // 创建空列表
    public static <E> LinkedList<E> emptyList(){
        return new EmptyList<>();
    }


    /**
     * 函数功能接口。一个参数的函数。
     */
    @FunctionalInterface
    public interface OneArgumentStatement<E> {
        void doSomething(E argument);
    }

    /**
     * 函数功能接口。转换元素，返回另一个类型。
     */
    @FunctionalInterface
    public interface OneArgumentExpression<E,R> {
        R compute(E argument);
    }

    /**
     * 函数功能接口，两个参数的表达式。
     */
    @FunctionalInterface
    public interface TwoArgumentExpression<A,B,R> {
        R compute(A lhs, B rhs);
    }

    /**
     * forEach 遍历，使用FunctionalInterface。
     */
    public void forEach(OneArgumentStatement<E> processor){
        processor.doSomething(head());
        tail().forEach(processor);
    }

    /**
     * map方法，
     */
    public <R> LinkedList<R> map(OneArgumentExpression<E,R> transformer){
        return new LinkedList<>(transformer.compute(head()),
                tail.map(transformer));
    }

    /**
     * fold 操作，如sum
     */
    public <R> R foldLeft(R initialValue, TwoArgumentExpression<R,E,R> computer){
        R newInitialValue = computer.compute(initialValue, head());
        return tail().foldLeft(newInitialValue, computer);
    }

    /**
     * 返回从start到end自增的list。如(1,100),生成1-99的列表。
     */
    private static LinkedList<Integer> ofRange(int start,int end,LinkedList<Integer> tailList){
        if (start >= end) {
            return tailList;
        } else {
            return ofRange(start +1, end, tailList).add(start);
        }
    }

    /**
     * 空列表构造ofRange
     */
    public static LinkedList<Integer> ofRange(int start, int end){
        return ofRange(start,end, LinkedList.emptyList());
    }

    /**
     * filter
     */
    public LinkedList<E> filter(OneArgumentExpression<E,Boolean> selector){
        if (selector.compute(head())) {
            return new LinkedList<E>(head(), tail.filter(selector));
        }else {
            return tail().filter(selector);
        }
    }

    /**
     * 打印重复的字符串seed。重复count次。
     */
    public static String repeatString(final String seed, int count){
        return LinkedList.ofRange(1,count+1).map((a)->seed).foldLeft("",(a,b)->a+b);
        // 或者 return LinkedList.ofRange(1,count+1).foldLeft("",(a,b)->a+seed);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = LinkedList.<Integer>emptyList()
                .add(5).add(3).add(0);
        //调用函数接口。打印
        linkedList.forEach((x) -> {System.out.println(x);});
        //使用静态方法lambda表达式
//        linkedList.forEach(System.out::println);

        //map方法
        LinkedList<Integer> tranformedList = linkedList.map((x)->x*2);
        tranformedList.forEach(System.out::println);

        //map转换为字符串
        LinkedList<String> tranformedListString = linkedList.map((x)->"x*2 = "+(x*2));
        tranformedListString.forEach(System.out::println);

        //fold操作--sum
        int sum = linkedList.foldLeft(0,(a,b)->a+b);
        System.out.println(sum);

        //fold操作--list添加元素
        LinkedList<Integer> reversedList = linkedList.foldLeft(LinkedList.emptyList(),(l,b)->l.add(b) );
        reversedList.forEach(System.out::println);

        //filter 过滤偶数
        LinkedList<Integer> evenList = LinkedList.ofRange(1,100).filter((a)->a%2==0);
        evenList.forEach(System.out::println);
    }
}


