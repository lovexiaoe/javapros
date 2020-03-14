package net.zhaoyu.javapros.j2se.Stream.create;

import com.zhaoyu.Stream.create.util.MySupplier;
import com.zhaoyu.Stream.create.util.Person;
import com.zhaoyu.Stream.create.util.PersonGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * 从不同的源中创建parallel stream
 */
public class Main {
    public static void main(String[] args) {
        // 从不同的源中创建 parallel stream
        System.out.printf("Main: Generating parallel streams from different sources\n");
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 从一个集合中创建stream，使用集合的parallelStream方法。
        System.out.printf("********************************************************\n");
        System.out.printf("From a Collection:\n");
        List<Person> persons = PersonGenerator.generatePersonList(10000);
        Stream<Person> personStream = persons.parallelStream();
        System.out.printf("Number of persons: %d\n", personStream.count());
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 使用Supplier接口创建Stream，parallel()方法转换为并行流，limit限制前10个元素
        System.out.printf("********************************************************\n");
        System.out.printf("From a Supplier:\n");
        Supplier<String> supplier = new MySupplier();
        Stream<String> generatorStream = Stream.generate(supplier);
        generatorStream.parallel().limit(10).forEach(s -> System.out.printf("%s\n", s));
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 从预定的元素集合中创建stream
        System.out.printf("********************************************************\n");
        System.out.printf("From a predefined set of elements:\n");
        Stream<String> elementsStream = Stream.of("Peter", "John", "Mary");
        elementsStream.parallel().forEach(element -> System.out.printf("%s\n", element));
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 从一个文件中创建stream
        System.out.printf("********************************************************\n");
        System.out.printf("From a File:\n");
        try (BufferedReader br = new BufferedReader(new FileReader("consumers\\nursery.data"));) {
            Stream<String> fileLines = br.lines();
            System.out.printf("Number of lines in the file: %d\n\n", fileLines.parallel().count());
            System.out.printf("********************************************************\n");
            System.out.printf("\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 从一个路径中
        System.out.printf("********************************************************\n");
        System.out.printf("From a Directory:\n");
        try {
            Stream<Path> directoryContent = Files.list(Paths.get(System.getProperty("user.home")));
            System.out.printf("Number of elements (files and folders):%d\n\n", directoryContent.parallel().count());
            directoryContent.close();
            System.out.printf("********************************************************\n");
            System.out.printf("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 从一个数组
        System.out.printf("********************************************************\n");
        System.out.printf("From an Array:\n");
        String array[] = { "1", "2", "3", "4", "5" };
        Stream<String> streamFromArray = Arrays.stream(array);
        streamFromArray.parallel().forEach(s -> System.out.printf("%s : ", s));
        System.out.printf("\n");
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 从随机数中创建
        System.out.printf("********************************************************\n");
        System.out.printf("Random number generators:\n");
        Random random = new Random();
        //创建出10个元素的stream
        DoubleStream doubleStream = random.doubles(10);
        //peek返回stream的连续的元素，average计算出stream的平均值。
        double doubleStreamAverage = doubleStream.parallel().peek(d -> System.out.printf("%f : ", d)).average()
                .getAsDouble();
        System.out.printf("\nDouble Stream Average: %f\n", doubleStreamAverage);
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

        // 连接两个stream为一个stream
        System.out.printf("********************************************************\n");
        System.out.printf("Concatenating streams:\n");
        Stream<String> stream1 = Stream.of("1", "2", "3", "4");
        Stream<String> stream2 = Stream.of("5", "6", "7", "8");
        Stream<String> finalStream = Stream.concat(stream1, stream2);
        finalStream.parallel().forEach(s -> System.out.printf("%s : ", s));
        System.out.printf("\n");
        System.out.printf("********************************************************\n");
        System.out.printf("\n");

    }
}
