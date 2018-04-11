package com.zhaoyu.threads.SynchronizationUtilities.Phaser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 在一个路径下查找指定后缀的文件。
 */
public class FileSearch implements Runnable{
    /**
     * 需要搜索的路径
     */
    private final String initPath;

    /**
     * 我们要查找的问题后缀
     */
    private final String fileExtension;

    /**
     * 存放我们查找的结果的全路径。
     */
    private List<String> results;

    /**
     * Phaser 用来控制文件搜索的执行。执行会被分为3个阶段， 1st: 使用后缀查询文件夹和子文件夹。
     * 2nd: 过滤结果，找到我们今天修改的文件。 3rd: 打印出结果。
     */
    private Phaser phaser;


    public FileSearch(String initPath, String fileExtension, Phaser phaser) {
        this.initPath = initPath;
        this.fileExtension = fileExtension;
        this.phaser = phaser;
        this.results = new ArrayList<>();
    }

    /**
     * TaskDelay method of the class. See the comments inside to a better description
     * of it
     */
    @Override
    public void run() {

        // Waits for the creation of all the FileSearch objects
        phaser.arriveAndAwaitAdvance();                             //线程创建后先进入第一阶段。

        System.out.printf("%s: Starting.\n", Thread.currentThread().getName());

        // 1st Phase: Look for the files
        File file = new File(initPath);
        if (file.isDirectory()) {
            directoryProcess(file);
        }else
        {
            return;
        }

        // If no results, deregister in the phaser and ends
        if (!checkResults()) {
            return;
        }

        // 2nd Phase: Filter the results
        filterResults();

        // If no results after the filter, deregister in the phaser and ends
        if (!checkResults()) {
            return;
        }

        // 3rd Phase: Show info
        showInfo();
        phaser.arriveAndDeregister();
        System.out.printf("%s: Work completed.\n", Thread.currentThread().getName());

    }

    /**
     * 打印出查找结果
     */
    private void showInfo() {
        for (int i = 0; i < results.size(); i++) {
            File file = new File(results.get(i));
            System.out.printf("%s: %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        // Waits for the end of all the FileSearch threads that are registered
        // in the phaser
        phaser.arriveAndAwaitAdvance();                       //线程进入下一个阶段。
    }

    /**
     * 该方法检查一个阶段执行后是否有结果。如果没有结果，从phaser中去除该线程的注册。
     * 如果有结果，则该阶段完成，等待下一个阶段。
     *
     * @return true if there are results, false if not
     */
    private boolean checkResults() {
        if (results.isEmpty()) {
            System.out.printf("%s: Phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase());
            System.out.printf("%s: Phase %d: End.\n", Thread.currentThread().getName(), phaser.getPhase());
            // 没有结果，该阶段完成，并不会进行下面的阶段，从phaser中去除该线程的注册 。
            phaser.arriveAndDeregister();
            return false;
        } else {
            // 有结果，则该阶段完成，等待下一个阶段
            System.out.printf("%s: Phase %d: %d results.\n", Thread.currentThread().getName(), phaser.getPhase(),
                    results.size());
            phaser.arriveAndAwaitAdvance();
            return true;
        }
    }

    /**
     * 过滤results,保留修改时间在一天内的。
     */
    private void filterResults() {
        List<String> newResults = new ArrayList<>();
        long actualDate = new Date().getTime();
        for (int i = 0; i < results.size(); i++) {
            File file = new File(results.get(i));
            long fileDate = file.lastModified();

            if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
                newResults.add(results.get(i));
            }
        }
        results = newResults;
    }

    /**
     * 在一个路径下查找指定后缀的文件。
     */
    private void directoryProcess(File file) {

        // Get the content of the directory
        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    // If is a directory, process it
                    directoryProcess(list[i]);
                } else {
                    // If is a file, process it
                    fileProcess(list[i]);
                }
            }
        }
    }

    /**
     * 查找指定后缀的文件。
     */
    private void fileProcess(File file) {
        if (file.getName().endsWith(fileExtension)) {
            results.add(file.getAbsolutePath());
        }
    }
}
