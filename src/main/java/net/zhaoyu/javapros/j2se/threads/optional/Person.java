package net.zhaoyu.javapros.j2se.threads.optional;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: Optional实例，构建一个多层使用Optional的案例。
 * @Author: zhaoyu
 * @Date: 2020/11/4
 */
@Getter
@Setter
public class Person {
    private Optional<Car> car;

    public static void main(String[] args) {
        //构建一个空对象
        Optional<Car> empty = Optional.empty();
        //构建一个非空对象，如果car为空，立即抛出NullPointerException
        Car car=new Car();
        Optional<Car> optCar = Optional.of(car);
        //构建一个空对象，如果car为空，Option返回空对象
        Car car1=null;
        Optional<Car> optCar1 = Optional.ofNullable(car1);

        //获取Option中的属性。如Insurance的name属性。
        Insurance insurance=new Insurance();
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name = optInsurance.map(Insurance::getName);
        System.out.println(name);

        //使用map由于返回参数封装了一层Optional，所以不能连续操作，flatMap直接返回Optional，没有包裹一层Option，所以使用flatMap可以获取
        // 更深层次的元素。
        Person person=new Person();
        Optional<Person> optPerson = Optional.of(person);
        String name1= optPerson.flatMap(Person::getCar)
                        .flatMap(Car::getInsurance)
                        .map(Insurance::getName).orElse("Unknown");

    }
}
