package net.zhaoyu.javapros.j2se.basic.interfaces.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Person("CEO")
@Person("Father")
class Man {
    String name;
}

/**
 * 注解类型是一种特殊的接口类型，为了和一般的interface做区分，通过@interface声明。
 * 所有的注解类型的父接口都是{@link java.lang.annotation.Annotation}，但是注解类不能声明父接口和父类。
 * 所有interface的规则都适用于注解。
 */

public class AnnotationTest {
    public static void main(String[] args) {
        //获取可重复注解
        if(Man.class.isAnnotationPresent(PersonContainer.class)) {
            PersonContainer p2=Man.class.getAnnotation(PersonContainer.class);
            for(Person t:p2.value()){
                System.out.println(t.value());
            }
        }
    }
}

/**
 * 一个被@Repeatable修饰的注解类型T是可重复多次使用的，它的value表示一个T的包含注解类型
 * （containing annotation type）。
 *  满足下列所有条件，则TC被称为注解T的”包含注解类型”。
 *  1. TC声明了返回值为T[]的value()方法。
 *  2. TC中除value方法的其他方法都有默认值。
 *  3. 使用@Retention注解，并且TC的保留时间大于或者等于T的保留时间。
 *      a. 如果TC的保留时间是{@link java.lang.annotation.RetentionPolicy.SOURCE},那么T的保留时间也是SOURCE。
 *      b. 如果TC的保留时间是CLASS,那么T的Retention是SOURCE或者CLASS。
 *      c. 如果TC的保留时间是RUNTIME,那么T的Retention是SOURCE或者CLASS或者RUNTIME。
 *  4. T使用的程序元素范围不小于TC的范围，通过@Target定义。假设T使用的集合为m1,TC使用的集合为m2。m2需要小于等于m1：
 *      a. m2的集合为{@link java.lang.annotation.ElementType.ANNOTATION_TYPE},m1需要是ANNOTATION_TYPE或TYPE或TYPE_USE。
 *      b. m2的集合为TYPE，则m1的集合为TYPE_USE或TYPE。
 *      c. m2的集合为TYPE_PARAMETER，m1的集合为TYPE_PARAMETER或TYPE_USE。
 *  5. T有类似于{@link java.lang.annotation.Documented}的元注解修饰，那么TC也需要有。
 *  6. T有类似于{@link java.lang.annotation.Inherited}的元注解修饰，那么TC也需要有。
 */

/**
 * Repeatable可以让注解在一个位置上多次使用（默认只能使用一次）。
 * 如下定义了Person注解，再定义一个Person的容器注解PersonContainer，value的返回值必须为Person[]。
 * 使用@Repeatable修饰Person后，Person就可以多次使用了
 */
@Repeatable(value = PersonContainer.class)
@interface Person {
    String value() default  "";
}


@Retention(RetentionPolicy.RUNTIME)
@interface PersonContainer {
    Person[] value(); //value的返回值必须为Person[]，PersonContainer称为Person的包含注解。
}

