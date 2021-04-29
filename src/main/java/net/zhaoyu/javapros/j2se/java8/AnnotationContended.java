package net.zhaoyu.javapros.j2se.java8;


/**
 * @sun.misc.Contended 是 Java 8 新增的一个注解，对某字段加上该注解则表示该字段会单独占用一个缓存行（Cache Line）。
 * 这里的缓存行是指 CPU 缓存（L1、L2、L3）的存储单元，常见的缓存行大小为 64 字节。
 * （注：JVM 添加 -XX:-RestrictContended 参数后 @sun.misc.Contended 注解才有效）
 *
 */
public class AnnotationContended {


    @sun.misc.Contended static final class CounterCell {
        volatile long value;
        CounterCell(long x) { value = x; }
    }
}
