package net.zhaoyu.javapros.j2se.Stream.collectelement;

import net.zhaoyu.javapros.j2se.Stream.collectelement.util.Counter;
import net.zhaoyu.javapros.j2se.Stream.collectelement.util.Person;
import net.zhaoyu.javapros.j2se.Stream.collectelement.util.PersonGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;


public class Main {

	public static void main(String args[]) {

		System.out.printf("********************************************************\n");
		System.out.printf("Main: Examples of collect methods.\n");

		// Generating a list of persons
		List<Person> persons = PersonGenerator.generatePersonList(100);
		System.out.printf("********************************************************\n");
		System.out.printf("\n");

		// Collecctors.groupingByConcurrent.
		/**
		 * 这里通过groupingByConcurrent按firstName分组，和数据库的分组类似，然后打印出每个分组的人数。
		 */
		System.out.printf("********************************************************\n");
		System.out.printf("Grouping By Concurrent\n");
		System.out.printf("Concurrent: %b\n", Collectors.groupingByConcurrent(p -> p).characteristics().contains(Characteristics.CONCURRENT));
		Map<String, List<Person>> personsByName = persons.parallelStream()
				.collect(Collectors.groupingByConcurrent(Person::getFirstName));
		personsByName.keySet().forEach(key -> {
			List<Person> listOfPersons = personsByName.get(key);
			System.out.printf("%s: There are %d persons with that name\n", key, listOfPersons.size());
		});
		System.out.printf("********************************************************\n");
		System.out.printf("\n");

		// Collectors.joining 这里将Person执行toString()后，使用逗号链接。
		System.out.printf("********************************************************\n");
		System.out.printf("Joining\n");
		System.out.printf("Concurrent: %b\n", Collectors.joining().characteristics().contains(Characteristics.CONCURRENT));
		String message = persons.parallelStream().map(p -> p.toString()).collect(Collectors.joining(","));
		System.out.printf("%s\n", message);
		System.out.printf("********************************************************\n");
		System.out.printf("\n");

		// Collectors.partitionBy 类似于数据库的分区，然后打印出每个分区的大小。
		System.out.printf("********************************************************\n");
		System.out.printf("Partitioning By\n");
		System.out.printf("Concurrent: %s\n", Collectors.partitioningBy(p-> true).characteristics().contains(Characteristics.CONCURRENT));
		Map<Boolean, List<Person>> personsBySalary = persons.parallelStream()
				.collect(Collectors.partitioningBy(p -> p.getSalary() > 50000));
		personsBySalary.keySet().forEach(key -> {
			List<Person> listOfPersons = personsBySalary.get(key);
			System.out.printf("%s: %d \n", key, listOfPersons.size());
		});
		System.out.printf("********************************************************\n");
		System.out.printf("\n");

		// Collectors.toConcurrentMap 转换为并发map，如果key有重复，那么使用第三个参数解决，合并相同key的value中，
		// 如 James taylor,James Jones 合并到Map中为 James: taylor,Jones
		System.out.printf("********************************************************\n");
		System.out.printf("To Concurrent Map\n");
		System.out.printf("Concurrent: %b\n",Collectors.toConcurrentMap(p -> p, p->p).characteristics().contains(Characteristics.CONCURRENT));
		ConcurrentMap<String, String> nameMap = persons.parallelStream().collect(
				Collectors.toConcurrentMap(p -> p.getFirstName(), p -> p.getLastName(), (s1, s2) -> s1 + ", " + s2));
		nameMap.forEach((key, value) -> {
			System.out.printf("%s: %s \n", key, value);
		});
		System.out.printf("********************************************************\n");
		System.out.printf("\n");

		// Collect, first example，第一个collect例子，将收入大于5万的手机到一个List中。
		System.out.printf("********************************************************\n");
		System.out.printf("Collect, first example\n");
		List<Person> highSalaryPeople = persons.parallelStream().collect(
				ArrayList::new,
					(list, person) -> {
					if (person.getSalary() > 50000) {
						list.add(person);
					}
				},
				ArrayList::addAll
		);
		System.out.printf("High Salary People: %d\n", highSalaryPeople.size());
		System.out.printf("********************************************************\n");
		System.out.printf("\n");

		// Collect, second example ，按照每一个FirstName统计人数，
		System.out.printf("********************************************************\n");
		System.out.printf("Collect, second example\n");
		ConcurrentHashMap<String, Counter> peopleNames = persons.parallelStream().collect(
				ConcurrentHashMap::new,
				(map, person) -> {
					map.computeIfPresent(person.getFirstName(), (name, counter) -> {
						counter.increment();
						return counter;
					});
					map.computeIfAbsent(person.getFirstName(), name -> {
						Counter c=new Counter();
						c.setValue(name);
						return c;
					});
				},
				(hash1, hash2) -> {
					hash2.forEach (10, (key, value) -> {
						hash1.merge(key, value, (v1,v2) -> {
							v1.setCounter(v1.getCounter()+v2.getCounter());
							return v1;
						});
					});
				}
		);

		peopleNames.forEach((name, counter) -> {
			System.out.printf("%s: %d\n", name, counter.getCounter());
		});

		System.out.printf("********************************************************\n");
		System.out.printf("\n");

	}

}