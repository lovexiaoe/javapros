package com.zhaoyu.threads.forkJoin.asynchronous;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountedCompleter;

public class FolderProcessor extends CountedCompleter<List<String>> {


    /**
     * Path of the folder this task is going to process
     */
    private String path;

    /**
     * Extension of the file the task is looking for
     */
    private String extension;

    /**
     * 子任务
     */
    private List<FolderProcessor> tasks;

    /**
     * 结果集
     */
    private List<String> resultList;

    /**
     * Constructor
     */
    protected FolderProcessor (CountedCompleter<?> completer, String path, String extension) {
        super(completer);
        this.path=path;
        this.extension=extension;
    }


    /**
     * Constructor
     */
    public FolderProcessor (String path, String extension) {
        this.path=path;
        this.extension=extension;
    }

    /**
     * 在这个任务中，遍历一个路径，
     * 如果是文件夹，对每个文件夹启动一个附加任务，
     * 如果是文件，那么匹配后缀。如果后缀相等，将路径加入结果集。
     */
    @Override
    public void compute() {
        resultList=new ArrayList<>();
        tasks=new ArrayList<>();

        File file=new File(path);
        File content[] = file.listFiles();
        if (content != null) {
            for (int i = 0; i < content.length; i++) {
                if (content[i].isDirectory()) {
                    // If is a directory, process it. Execute a new Task
                    FolderProcessor task=new FolderProcessor(this,content[i].getAbsolutePath(), extension);
                    task.fork();
                    //附加任务计数
                    addToPendingCount(1);
                    tasks.add(task);
                } else {
                    // If is a file, process it. Compare the extension of the file and the extension
                    // it's looking for
                    if (checkFile(content[i].getName())){
                        resultList.add(content[i].getAbsolutePath());
                        System.out.printf("Result found: %s\n", content[i].getAbsolutePath());
                    }
                }
            }

            // If the number of tasks thrown by this tasks is bigger than 50, we write a message
            if (tasks.size()>50) {
                System.out.printf("%s: %d tasks ran.\n",file.getAbsolutePath(),tasks.size());
            }

        }

        // Try the completion of the task
        tryComplete();
    }


    //完成时，
    @Override
    public void onCompletion(CountedCompleter<?> completer) {
        for (FolderProcessor childTask : tasks) {
            resultList.addAll(childTask.getResultList());
        }
    }


    /**
     * Checks if a name of a file has the extension the task is looking for
     * @param name name of the file
     * @return true if the name has the extension or false otherwise
     */
    private boolean checkFile(String name) {
        if (name.endsWith(extension)) {
            return true;
        }
        return false;
    }

    /**
     * Method that return the list of results
     * @return the resultList
     */
    public List<String> getResultList() {
        return resultList;
    }



}
