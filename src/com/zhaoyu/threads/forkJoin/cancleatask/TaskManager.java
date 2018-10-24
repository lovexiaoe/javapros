package com.zhaoyu.threads.forkJoin.cancleatask;


import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Class that stores all the tasks that have been sent to
 * a ForkJoinPool. Provides a method for the cancellation of
 * all the tasks
 *
 */
public class TaskManager {

    /**
     * List of tasks
     */
    private final ConcurrentLinkedDeque<SearchNumberTask> tasks;

    /**
     * Constructor of the class. Initializes the list of tasks
     */
    public TaskManager(){
        tasks=new ConcurrentLinkedDeque<>();
    }

    /**
     * Method to add a new Task in the list
     * @param task The new task
     */
    public void addTask(SearchNumberTask task){
        tasks.add(task);
    }

    /**
     * Method that cancel all the tasks in the list
     * @param cancelTask
     */
    public void cancelTasks(SearchNumberTask cancelTask){
        for (SearchNumberTask task  :tasks) {
            if (task!=cancelTask) {
                task.cancel(true);
                task.logCancelMessage();
            }
        }
    }
}