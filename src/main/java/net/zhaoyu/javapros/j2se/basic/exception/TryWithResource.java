package net.zhaoyu.javapros.j2se.basic.exception;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: java7后提供了新语法try-with-resources, 可以自动关闭实现了AutoCloseable或者Closeable接口的资源。
 *              在try语句结束后，不论其包括的代码是正常执行完毕还是发生异常，都会自动调用对应的Close方法，释放资源。
 *
 *              也可以使用try-with-resources声明多个资源，这些资源会以声明相反的顺序关闭。
 *
 *              和try-catch-finally不同的是，如果try-with-resources语句和try模块都抛出异常，try-with-resources语句中
 *              的异常会被抑制，优先抛出try代码块中的异常。
 *
 * @Author: zhaoyu
 * @Date: 2020/10/20
 */
public class TryWithResource {
    public static void main(String[] args) {
        //如下，所有的Writer都实现了Closeable接口，所以可以使用try-with-resources.
        try (FileWriter file = new FileWriter("log.txt"); PrintWriter pw = new PrintWriter(file)) {
            pw.println("try-with-resources test!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果try-with-resources和catch-finally一起使用，catch和finally子句将会在try-with-resources子句中打开的资源被关闭之后得到调用。
     * 即catch和finally不影响try-with-resources生效。
     */
    void TryWithResourcesFinally(){
        //如下，所有的Writer都实现了Closeable接口，所以可以使用try-with-resources.
        try (FileWriter file = new FileWriter("log.txt"); PrintWriter pw = new PrintWriter(file)) {
            pw.println("try-with-resources test!");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("final statement!");
        }
    }
}
