package net.zhaoyu.javapros.j2se.Stream.mapreduce;

import net.zhaoyu.javapros.j2se.Stream.mapreduce.util.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

//说明reduce方法的几种使用版本。
public class Main {
    public static void main(String args[]) {

        System.out.printf("Main: reduce方法的例子.\n");

        // 生成double类型的List
        System.out.printf("Main: 创建一个double的list.\n");
        List<Double> numbers= DoubleGenerator.generateDoubleList(10000, 1000);
        System.out.printf("\n");

        // 从list中生成DoubleStream
        System.out.printf("********************************************************\n");
        DoubleStream doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        long numberOfElements = doubleStream.parallel().count();
        System.out.printf("The list of numbers has %d elements.\n", numberOfElements);
        System.out.printf("\n");

        // 取得sum
        System.out.printf("********************************************************\n");
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        double sum = doubleStream.parallel().sum();
        System.out.printf("Its numbers sum %f.\n", sum);
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 取得平均数
        System.out.printf("********************************************************\n");
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        double average = doubleStream.parallel().average().getAsDouble();
        System.out.printf("Its numbers have an average value of %f.\n", average);
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 取得最大值
        System.out.printf("********************************************************\n");
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        double max = doubleStream.parallel().max().getAsDouble();
        System.out.printf("The maximum value in the list is %f.\n", max);
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 取得最小值
        System.out.printf("********************************************************\n");
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        double min = doubleStream.parallel().min().getAsDouble();
        System.out.printf("The minimum value in the list is %f.\n", min);
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // reduce方法，第一个版本，计算所有point的x的和及y的和。Optional用Point作为参数化
        // 该版本的reduce只有一个参数，BinaryOperator，如果stream中没有元素，那么Optional对象将为空。
        System.out.printf("********************************************************\n");
        System.out.printf("Reduce - First Version\n");
        List<Point> points= PointGenerator.generatePointList(10000);
        Optional<Point> point=points.parallelStream().reduce((p1, p2) -> {
            Point p=new Point();
            p.setX(p1.getX()+p2.getX());
            p.setY(p1.getY()+p2.getY());
            return p;
        });
        System.out.println(point.get().getX()+":"+point.get().getY());
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // reduce方法，第二个版本，map到salary字段,然后计算总salary，在这个版本中给出identity参数，如果
        // stream中没有值，那么返回这个identity
        System.out.printf("********************************************************\n");
        System.out.printf("Reduce, second version\n");
        List<Person> persons = PersonGenerator.generatePersonList(10000);

        long totalSalary=persons.parallelStream().map(p -> p.getSalary()).reduce(0, (s1,s2) -> s1+s2);
        System.out.printf("Total salary: %d\n",totalSalary);
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // Reduce 方法，第三个版本，这个版本用于reduce计算的结果的类型和stream中的类型不一样的情况，
        // 这个版本的reduce有3个参数，第一个为identity，第二个为类型是BiFunction的accumulator,接收一个返回类型的对象
        // 和一个stream的元素, 生成一个返回类型的值，第三个为combiner,实现->二进制操作符
        // 最后我们计算出薪酬大于50000的人数。
        System.out.printf("********************************************************\n");
        System.out.printf("Reduce, third version\n");
        Integer value=0;
        value=persons.parallelStream().reduce(value, (n,p) -> {
            if (p.getSalary() > 50000) {
                return n+1;
            } else {
                return n;
            }
        }, (n1,n2) -> n1+n2);
        System.out.printf("The number of people with a salary bigger that 50,000 is %d\n",value);
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

    }
}
