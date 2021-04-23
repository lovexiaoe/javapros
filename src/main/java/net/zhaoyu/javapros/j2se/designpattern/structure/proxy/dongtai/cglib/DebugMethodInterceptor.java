package net.zhaoyu.javapros.j2se.designpattern.structure.proxy.dongtai.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 调试信息代理类。
 */
public class DebugMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("方法开始" + method.getName());

        //从invokeSuper方法来看，cglib的动态代理通过继承完成，
        Object obj = methodProxy.invokeSuper(o, args);
        System.out.println("方法完成"+method.getName());
        return obj;
    }
}
