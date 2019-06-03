package com.zhaoyu.Stream.applyanaction;

import com.zhaoyu.Stream.applyanaction.util.Person;
import com.zhaoyu.Stream.applyanaction.util.PersonGenerator;
import com.zhaoyu.Stream.applyanaction.util.DoubleGenerator;

import java.util.List;

/**
 * 本例中，我们主要使用一些方法，应用一个动作到stream中的所有元素中，我们使用两个terminal 操作：forEach和forEachOrdered，
 * 和一个intermediate 操作，peek()。
 * peek方法和forEach类似，但是是一个intermediate操作，通常用于日志。
 */
public class Main {
    public static void main(String[] args) {

        List<Person> persons= PersonGenerator.generatePersonList(10);

        // For each parallel
        System.out.printf("********************************************************\n");
        System.out.printf("Parallel forEach()\n");
        persons.parallelStream().forEach(p -> {
            System.out.printf("%s, %s\n", p.getLastName(), p.getFirstName());
        });
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // For each ordered
        List<Double> doubles= DoubleGenerator.generateDoubleList(10, 100);

        // For each ordered parallel with numbers
        System.out.printf("********************************************************\n");
        System.out.printf("Parallel forEachOrdered() with numbers\n");
        doubles.parallelStream().sorted().forEachOrdered(n -> {
            System.out.printf("%f\n",n);
        });
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // For each after sort with numbers
        System.out.printf("********************************************************\n");
        System.out.printf("Parallel forEach() after sorted() with numbers\n");
        // sorted但是没有使用forEachOrdered，不能按照顺序输出。
        doubles.parallelStream().sorted().forEach(n -> {
            System.out.printf("%f\n",n);
        });
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // For each ordered parallel with objects
        System.out.printf("********************************************************\n");
        System.out.printf("Parallel forEachOrdered with Persons\n");
        persons.parallelStream().sorted().forEachOrdered( p -> {
            System.out.printf("%s, %s\n", p.getLastName(), p.getFirstName());
        });
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // Peek
        System.out.printf("********************************************************\n");
        System.out.printf("Peek\n");
        //step1打印出所有数字，step2答应出小鱼50的数字，final step,使用forEach打印，是一个terminal操作。
        //每个流操作必须有一个terminal操作，如果没有，那么定义的中间操作是不会执行的。如下去掉forEach，可以运行，但Peek不会执行打印。
        doubles
                .parallelStream()
                .peek(d -> System.out.printf("Step 1: Number: %f\n",d))
                .filter( n -> n < 50)
                .peek(d -> System.out.printf("Step 2: Number: %f\n",d))
                .forEach(d -> System.out.printf("Final Step: Number: %f\n",d));
        System.out.printf("********************************************************\n");


    }
}
