package com.zhaoyu.threads.SynchronizationUtilities.Exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * exchanger用于两个任务的数据交换，详细的说，exchanger定义了两个线程之间的同步点。一个线程会sleep等待另一个线程到达同步点，
 * 当两个线程到达这个点后，将两个线程
 * 拥有的相同类型的数据结构的数据交换（如A线程的list和B线程的list交换。）
 *
 * 在生产者和消费者问题中，这个类很有用。
 */
public class Main {
    public static void main(String[] args) {

        // Creates two buffers
        List<String> buffer1=new ArrayList<>();
        List<String> buffer2=new ArrayList<>();

        // Creates the exchanger
        Exchanger<List<String>> exchanger=new Exchanger<>();

        // Creates the producer
        Producer producer=new Producer(buffer1, exchanger);
        // Creates the consumer
        Consumer consumer=new Consumer(buffer2, exchanger);

        // Creates and starts the threads
        Thread threadProducer=new Thread(producer);
        Thread threadConsumer=new Thread(consumer);

        threadProducer.start();
        threadConsumer.start();

    }
}
