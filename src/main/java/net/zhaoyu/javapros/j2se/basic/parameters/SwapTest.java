package net.zhaoyu.javapros.j2se.basic.parameters;

public class SwapTest {
    public static void main(String[] args) {
        TestSwap();
    }

    /**
     * java中的参数都是值传递，所以不能改变参数，但是可以改变应用参数的属性值，如数组中的元素。
     */
    public static void TestSwap(){
        int a=1,b=2;
        System.out.println("int交换前，a="+a+",b="+b);
        swapInt(a,b);
        System.out.println("int交换后，a="+a+",b="+b);
        Integer c=3,d=4;
        System.out.println("Integer交换前，c="+c+",d="+d);
        swapInteger(c,d);
        System.out.println("Integer交换后，c="+c+",d="+d);
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
