package net.zhaoyu.javapros.j2se.typesvaluesvariables.parameterizedtype;

/**
 * 类型引元（Type Arguments），通常作为parameterizedType的参数列表。
 *
 * 如果有如下情况，说明两个类型引元是不相等的:
 * 1，引元都不是Type variable或通配符。且类型也不同。如：Integer和String
 * 2，一个引元是Type variable或者通配符拥有一个上限S，另一个引元T不是Type variable或通配符;且不满足|S|<:|T|或|T|<:|S|。
 *    如：<? extends String>和 Integer。
 * 3，两个引元是type variable或者通配符，上限分别为S,T；且不满足|S|<:|T|或|T|<:|S|。如：<? extends String>和T不相等，T和S也不相等。
 *
 * 类型引元可以比较大小：当一个引元T1的范围包含另一个引元T2时，我们可以写为T2<=T1。如：
 * • ? extends T <= ? extends S if T <: S
 * • ? extends T <= ?
 * • ? super T <= ? super S if S <: T
 * • ? super T <= ?
 * • ? super T <= ? extends Object
 * • T <= T
 * • T <= ? extends T
 * • T <= ? super T
 */
public class TypeArgumentsTest {
}
