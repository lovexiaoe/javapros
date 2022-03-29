package net.zhaoyu.javapros.j2se.typesvaluesvariables.referencetype;

/**
 * 引用类型包括了：Class,Interface,type variables 和 array。
 *
 * 引用类型在编译时相同：拥有相同的二进制名称和类型参数。当引用类型相同，可以称他们为相同的类或者接口。
 * 在运行时，二进制名称相同的引用类型可能被不同的classLoader同时加载。他们是有区别的。
 *
 * 所以在运行时，两种引用类型是相同的运行时类型，如下：
 * 1. 相同的二进制名称，也需要有相同的classLoader。
 * 2. 同时是数组类型，且元素也是运行时相同的类型。
 */
public class ReferenceTest {

}
