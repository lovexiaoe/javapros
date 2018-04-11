package com.zhaoyu.threads.SynchronizationUtilities.Exchanger;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Producer implements Runnable{
    /**
     * Buffer to save the events produced
     */
    private List<String> buffer;

    /**
     * Exchager to synchronize with the consumer
     */
    private final Exchanger<List<String>> exchanger;


    public Producer (List<String> buffer, Exchanger<List<String>> exchanger){
        this.buffer=buffer;
        this.exchanger=exchanger;
    }

    /**
     * 分10次循环，每次产生10个事件，当产生10个循环后，和consumer进行一次数据交换。
     * 发给消费者10个事件的buffer，然后收到一个空的buffer。
     */
    @Override
    public void run() {

        for (int cycle=1; cycle<=10; cycle++){
            System.out.printf("Producer: Cycle %d\n",cycle);

            for (int j=0; j<10; j++){
                String message="Event "+(((cycle-1)*10)+j);
                System.out.printf("Producer: %s\n",message);
                buffer.add(message);
            }

            try {
                buffer=exchanger.exchange(buffer);          //进行数据交换
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Producer buffer size: %d\n",buffer.size()); //0,每次数据交换后，剩余为0
        }

    }
}
