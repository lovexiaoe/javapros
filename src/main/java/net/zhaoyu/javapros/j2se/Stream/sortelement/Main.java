package net.zhaoyu.javapros.j2se.Stream.sortelement;

import com.zhaoyu.Stream.sortelement.util.Person;
import com.zhaoyu.Stream.sortelement.util.PersonGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 对stream中的元素排序，主要使用sorted方法和unsorted方法，
 * sorted方法结合forEachOrdered使用。
 */
public class Main {
    public static void main(String args[]) {

        // Sorted array of integer
        int[] numbers={9,8,7,6,5,4,3,2,1,2,3,4,5,6,7,8,9};
        System.out.printf("********************************************************\n");
        Arrays.stream(numbers).parallel().sorted().forEachOrdered(n -> {
            System.out.printf("%d\n", n);
        });
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // Sorted for Persons
        System.out.printf("********************************************************\n");
        List<Person> persons= PersonGenerator.generatePersonList(10);
        persons.parallelStream().sorted().forEachOrdered(p -> {
            System.out.printf("%s, %s\n",p.getLastName(),p.getFirstName());
        });
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // Unordered，取消stream的排序，当使用limit(1)取第一个元素时，取到的是随机的。
        System.out.printf("********************************************************\n");
        Person person=persons.get(0);
        System.out.printf("%s %s\n", person.getFirstName(),person.getLastName());

        TreeSet<Person> personSet=new TreeSet<>(persons);
        for (int i=0; i<10; i++) {
            System.out.printf("********** %d ***********\n",i);
            person=personSet.stream().parallel().limit(1).collect(Collectors.toList()).get(0);
            System.out.printf("%s %s\n", person.getFirstName(),person.getLastName());

            person=personSet.stream().unordered().parallel().limit(1).collect(Collectors.toList()).get(0);
            System.out.printf("%s %s\n", person.getFirstName(),person.getLastName());
            System.out.printf("*************************\n",i);
        }

    }
}
