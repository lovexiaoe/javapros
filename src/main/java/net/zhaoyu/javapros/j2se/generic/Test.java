package net.zhaoyu.javapros.j2se.generic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用于说明<T extends Comparable<? super T>> 这个复杂泛型表达式的使用。
 */

public class Test {
    /**
     * 排序方法一，使用<T extends Comparable<T>>,匹配泛型<Animal extends Comparable<Animal>>。
     */
    public static <T extends Comparable<T>> void mySort1(List<T> list)
    {
        Collections.sort(list);
    }

    /**
     * 排序方法二，使用<T extends Comparable<? super T>>，匹配泛型<Dog extends Comparable<Animal>>
     * 因为Dog只实现了Comparable接口的compareTo(Animal)方法， 并没有实现compareTo(Dog)，所以Dog是匹配的。
     */
    public static <T extends Comparable<? super T>> void mySort2(List<T> list)
    {
        Collections.sort(list);
    }

    public static void main(String[] args) {
        sort1Test();
        sort2Test();
    }

    /**
     * 使用sort1排序，编译错误。
     */
    private static void sort1Test() {
        // 创建一个 Animal List
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(25));
        animals.add(new Dog(35));

        // 创建一个 Dog List
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog(5));
        dogs.add(new Dog(10));

        // 测试  mySort1() 方法
        mySort1(animals);
//        mySort1(dogs); //编译错误，
    }

    /**
     * 使用sort2排序。
     */
    private static void sort2Test(){
        // 创建一个 Animal List
        List<Animal> animals = new ArrayList<Animal>();
        animals.add(new Animal(25));
        animals.add(new Dog(35));

        // 创建一个 Dog List
        List<Dog> dogs = new ArrayList<Dog>();
        dogs.add(new Dog(5));
        dogs.add(new Dog(18));

        // 测试  mySort2() 方法
        mySort2(animals);
        mySort2(dogs);
    }

}

class Animal implements Comparable<Animal> {
    protected int age;
    public Animal(int age)

    {
        this.age = age;
    }

    @Override
    public int compareTo(Animal other)
    {
        return this.age - other.age;
    }
}

/**
 * 实现了Compare接口的方法compareTo(Animal)，是继承自Animal中的。并没有实现compareTo(Dog)
 */
class Dog extends Animal
{
    public Dog(int age)
    {
        super(age);
    }
}