package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CompletableFuture;

import java.util.List;
import java.util.function.Function;

/**
 * java1.8 ,Function接口接收一个参数，返回一个结果
 *
 */
public class NumberSelector implements Function<List<Long>, Long> {

    @Override
    public Long apply(List<Long> list) {

        System.out.printf("%s: Step 3: Start\n",Thread.currentThread().getName());
        long max=list.stream().max(Long::compare).get();  //计算最大值，Stream是1.8提供的类
        long min=list.stream().min(Long::compare).get();  //计算最小值，Stream是1.8提供的类
        long result=(max+min)/2;
        System.out.printf("%s: Step 3: Result - %d\n",Thread.currentThread().getName(), result);
        return result;
    }

}