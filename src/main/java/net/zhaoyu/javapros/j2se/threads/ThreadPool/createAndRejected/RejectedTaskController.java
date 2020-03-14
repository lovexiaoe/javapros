package net.zhaoyu.javapros.j2se.threads.ThreadPool.createAndRejected;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedTaskController implements RejectedExecutionHandler {

    /**
     * 该方法在RejectedExecutionHandler接口中定义，当拒绝一个任务时，执行此方法。
     * @param r TaskPeriodically that has been rejected
     * @param executor Executor that has rejected the task
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.printf("RejectedTaskController: The task %s has been rejected\n",r.toString());
        System.out.printf("RejectedTaskController: %s\n",executor.toString());
        System.out.printf("RejectedTaskController: Terminating: %s\n",executor.isTerminating()); //executor是否正在结束
        System.out.printf("RejectedTaksController: Terminated: %s\n",executor.isTerminated());//executor是否已经结束
    }

}
