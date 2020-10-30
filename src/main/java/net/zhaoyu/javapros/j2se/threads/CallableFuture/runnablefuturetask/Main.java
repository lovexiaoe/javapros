package net.zhaoyu.javapros.j2se.threads.CallableFuture.runnablefuturetask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zhaoyu
 * @Date: 2020/10/24
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        FileSearch system=new FileSearch("C:\\Windows", "log");
        FileSearch apps=new FileSearch("C:\\Program Files","log");
        FileSearch documents=new FileSearch("C:\\Documents And Settings","log");

        Task systemTask=new Task(system,null);
        Task appsTask=new Task(apps,null);
        Task documentsTask=new Task(documents,null);

        executor.submit(systemTask);
        executor.submit(appsTask);
        executor.submit(documentsTask);

        executor.shutdown();

        try {
            //等待线程池所有任务执行结束。
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //在FutureTask运行Runnable对象时，你通过Future对象的get方法会返回null，只有通过FutureTask.get方法获取结果。
        try {
            System.out.printf("Main: System task: Number of Results: %d\n",systemTask.get().size());
            System.out.printf("Main: App task: Number of Results: %d\n",appsTask.get().size());
            System.out.printf("Main: App task: Documents of Results: %d\n",documentsTask.get().size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
