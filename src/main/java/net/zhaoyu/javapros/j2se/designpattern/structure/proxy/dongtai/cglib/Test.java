package net.zhaoyu.javapros.j2se.designpattern.structure.proxy.dongtai.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * CGLIB(Code Generation Library)是一个基于ASM的字节码生成库，它允许我们在运行时对字节码进行修改和动态生成。
 * CGLIB 通过继承方式实现代理，因此不能代理声明为 final 类型的类和方法。
 */
public class Test {
    public static void main(String[] args) {

        AliSmsService aliSmsService = (AliSmsService) getProxy(AliSmsService.class);
        aliSmsService.send("你好！");
    }

    public static Object getProxy(Class<?> clazz) {
        Enhancer enhancer=new Enhancer();
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new DebugMethodInterceptor());
        Object obj=enhancer.create();
        return obj;
    }
}
