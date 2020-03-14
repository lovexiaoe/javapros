package net.zhaoyu.javapros.j2se.threads.ThreadPool.executorInvoke;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * invokeAny 在线程池中有一个成功执行，就会返回。下面例子在多个系统中验证用户信息，
 * 如果有一个系统验证成功，则线程池直接返回。
 *
 * 线程池中判断一个线程是否完成，使用isDone。
 * awaitTermination()方法等待线程池中所有任务执行完并调用shutdown关闭线程池。
 * invokeAll 等待线程池执行完所有的任务，类似于门栓，它将等待的任务列表的执行完成，并不会关闭线程池。
 */
public class Main {
    public static void main(String[] args) {

        // Initialize the parameters of the user
        String username="test";
        String password="pass";


        // Create two tasks for the user validation objects
        ValidatorTask ldapTask=new ValidatorTask("ldapSystem", username, password);
        ValidatorTask dbTask=new ValidatorTask("ldapSystem",username,password);

        // Add the two tasks to a list of tasks
        List<ValidatorTask> taskList=new ArrayList<>();
        taskList.add(ldapTask);
        taskList.add(dbTask);

        // Create a new Executor
        ExecutorService executor=(ExecutorService) Executors.newCachedThreadPool();
        String result;
        try {
            // 发送任务列表给执行器，等待第一个没有抛出异常的任务执行完成，并取得结果。
            // 如果所有的任务都抛出异常，则方法抛出ExecutionException。
            result = executor.invokeAny(taskList);
            System.out.printf("TaskDelay: Result: %s\n",result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.printf("TaskDelay: End of the Execution\n");
    }
}
