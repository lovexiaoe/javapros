package net.zhaoyu.javapros.j2se.functionalprogramming.lambda;

/**
 * lambda表达式除了传入参数外，还可以传入本地变量。但是本地变量必须使用final声明或者实际上是final的。
 */
public class LocalVariableTest {
    public static void main(String[] args) {
        int portNum=1337;
        final int finalPortNum = portNum;
        Runnable r = () -> System.out.println(finalPortNum);
        //这里修改portNum,那么前面的lambda表达式引用protNum必须是实际上final的，本地变量只会被获取一次值。所以lambda需要引用
        // 复制的另一个变量finalPortNum
        portNum=331;
    }
}
