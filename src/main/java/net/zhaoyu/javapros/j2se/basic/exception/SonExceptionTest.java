package net.zhaoyu.javapros.j2se.basic.exception;

import java.io.IOException;
import java.net.BindException;

/**
 * 抛出异常的测试，子类的方法只能跑出抛出父类方法异常的子异常。
 */
public class SonExceptionTest {
    public static void main(String[] args) throws IOException {
        Test test = new TestSon();
        test.foo();
    }

}
class Test{
    public void foo()  throws IOException{
        System.out.println("Father Method");
    }
}

class TestSon extends Test{

    /**
     * IOException是BindException的父类，所以会出现编译错误。子类方法抛出的异常不能大于父类的异常。
     * @throws IOException
     */
    @Override
    public void foo() throws IOException {
        try {
            System.out.println("Son Method!");
        } catch (Exception e) {
            throw new IOException();
        }
    }
}

