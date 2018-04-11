package com.zhaoyu.threads.ThreadPool.createAndRejected;

/**
 * 这里使用一个线程池进行任务的执行。线程池在关闭后，停止接收新的任务，已经接收的任务会继续执行直到完成。
 * 使用拒绝处理器拒绝新接收的任务。
 */
public class Main {
    public static void main(String[] args) {
        // Create the server
        Server server=new Server();

        // 发送100个请求任务给server
        System.out.printf("TaskDelay: Starting.\n");
        for (int i=0; i<100; i++){
            Task task=new Task("TaskPeriodically "+i);
            server.executeTask(task);
        }

        // 关闭 server
        System.out.printf("TaskDelay: Shuting down the Executor.\n");
        server.endServer();

        // 关闭server后，再次发送任务会被拒绝。
        System.out.printf("TaskDelay: Sending another TaskPeriodically.\n");
        Task task=new Task("Rejected task");
        server.executeTask(task);

        System.out.printf("TaskDelay: End.\n");
    }
}
