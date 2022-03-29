package net.zhaoyu.javapros.j2se.conversion;

import java.util.Collection;

/**
 * 未使用泛型的遗留代码，用到了后来已使用泛型机制的类库，在这种情况下，遗留代码使用的是原生类型（如使用Collection而不是Collection<String>)。
 * 在这种情况下会产生非检查警告。
 *
 * Unchecked Conversion的定义为：
 * 假设G是带有n个类型引元的泛型，则：
 * 存在从原生类或者接口G到任意参数化类型G<T1,...,Tn>的转换，即G可以转换为G<String,Integer>。
 * 存在从原生类或者接口G的k维数组G[]k到任意参数化类型数组G<T1,...,Tn>[]k的转换。
 *
 * 使用Unchecked Conversion会导致编译期的非检查警告，可以使用SuppressWarning注解限制。
 */
public class UncheckedConversion {
    public static void main(String[] args) {

    }
}
