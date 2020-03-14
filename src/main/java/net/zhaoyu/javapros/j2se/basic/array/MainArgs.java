package net.zhaoyu.javapros.j2se.basic.array;

/**
 * Main方法的入参也是一个数组，它的长度是传入数组的长度，访问超过传入的长度会出现<数组越界异常>
 * 如下面方法，执行java MainArgs a b c。那么程序显示正常。而运行java MainArgs a b。args数组长度为2，
 * 程序取不不到数组的第三个元素，出现越界异常。
 */
public class MainArgs {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println(args[i]);
        }
    }
}
