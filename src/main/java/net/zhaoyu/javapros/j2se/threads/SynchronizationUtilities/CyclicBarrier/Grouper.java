package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CyclicBarrier;

/**
 * 将每一行查找的结果累加。计算最终的结果。当所有的查找结束后，CyclicBarrier会自动执行这个类的线程。
 */
public class Grouper implements Runnable{
    //查找结果
    private final Results results;

    public Grouper(Results results){
        this.results=results;
    }

    /**
     * 累加查找的所有结果
     */
    @Override
    public void run() {
        int finalResult=0;
        System.out.printf("Grouper: Processing results...\n");
        int data[]=results.getData();
        for (int number:data){
            finalResult+=number;
        }
        System.out.printf("Grouper: Total result: %d.\n",finalResult);
    }
}
