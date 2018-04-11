package com.zhaoyu.threads.SynchronizationUtilities.Exchanger;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Consumer implements Runnable{
    /**
     * Buffer to save the events produced
     */
    private List<String> buffer;

    /**
     * Exchager to synchronize with the consumer
     */
    private final Exchanger<List<String>> exchanger;

    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger){
        this.buffer=buffer;
        this.exchanger=exchanger;
    }

    /**
     * 消费所有的Producer产生的事件，在处理完10个事件后，它使用exchanger和Producer同步，
     * 发出一个空的buffer会收到有10个事件的buffer。
     *
     * 循环执行10次数据交换和消费。
     */
    @Override
    public void run() {

        for (int cycle = 1; cycle <= 10; cycle++){
            System.out.printf("Consumer: Cycle %d\n",cycle);

            try {
                // Wait for the produced data and send the empty buffer to the producer
                buffer=exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Consumer buffer size: %d\n",buffer.size());  //10 在数据交换后得到10个事件。

            for (int j=0; j<10; j++){
                String message=buffer.get(0);
                System.out.printf("Consumer: %s\n",message);
                buffer.remove(0);
            }

        }

    }
}
