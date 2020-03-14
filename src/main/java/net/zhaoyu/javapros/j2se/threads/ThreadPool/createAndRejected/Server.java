package net.zhaoyu.javapros.j2se.threads.ThreadPool.createAndRejected;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private final ThreadPoolExecutor executor;

    public Server(){
        // 创建一个executor并设置任务拒绝处理器
        executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        RejectedTaskController controller=new RejectedTaskController();
        executor.setRejectedExecutionHandler(controller);
    }

    /**
     * 使用Executor执行任务，并打印线程池的状态。
     * @param task The request made to the server
     */
    public void executeTask(Task task){
        System.out.printf("Server: A new task has arrived\n");
        executor.execute(task);                   //execute接收Runnable参数。
        System.out.printf("Server: Pool Size: %d，Active Count: %d,TaskPeriodically Count: %d,Completed Tasks: %d\n",
                executor.getPoolSize(),executor.getActiveCount(),executor.getTaskCount(),executor.getCompletedTaskCount());
    }

    public void endServer() {
        executor.shutdown();
    }
}