package net.zhaoyu.javapros.j2se.basic.parameters;

public class SwapTest {
    public static void main(String[] args) {
        TestSwap();
    }

    /**
     * java中的参数都是值传递，所以不能改变参数。
     * 所以对参数整体的改动都不会改变原对象，也不会让对象引用一个新对象。只有对内部结构进行改变，如person.name,array[2]等。
     */
    public static void TestSwap(){
        int a=1,b=2;
        System.out.println("int交换前，a="+a+",b="+b);
        swapInt(a,b);
        System.out.println("int交换后，a="+a+",b="+b); //不会改变
        Integer c=3,d=4;
        System.out.println("Integer交换前，c="+c+",d="+d);
        swapInteger(c,d);
        System.out.println("Integer交换后，c="+c+",d="+d); //不会改变
    }

    public  static void swapInt(int a,int b){
        int c=b;
        b=a;
        a=c;
    }
    public  static void swapInteger(Integer a,Integer b){
        Integer c=b;
        b=a;
        a=c;
    }

}
