package net.zhaoyu.javapros.j2se.threads.concurrentdesign.stream;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * 在大数据集时，使用parallel stream处理，stream简单更易于理解，且性能也不差。
 * 本例实际测试，forkjoin性能还是要高一些。
 * Collect: 10 - 67
 * Collect ForkJoinPool: 10 - 20
 */
public class Main {

	public static void main(String[] args) {
		List<Person> persons = PersonGenerator.generatePersonList(100000);

		Date start, end;

		start = new Date();
		Map<String, List<Person>> personsByName = persons.parallelStream()
				.collect(Collectors.groupingByConcurrent(p -> p.getFirstName()));
		end = new Date();

		System.out.printf("Collect: %d - %d\n", personsByName.size(), end.getTime() - start.getTime());

		start = new Date();
		ConcurrentHashMap<String, ConcurrentLinkedDeque<Person>> forkJoinMap = new ConcurrentHashMap<>();
		PersonMapTask personMapTask = new PersonMapTask(persons, forkJoinMap);
		ForkJoinPool.commonPool().invoke(personMapTask);
		end = new Date();

		System.out.printf("Collect ForkJoinPool: %d - %d\n", forkJoinMap.size(), end.getTime() - start.getTime());
	}

}
