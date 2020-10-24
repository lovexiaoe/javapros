package net.zhaoyu.javapros.j2se.threads.CallableFuture.runnablefuturetask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 定义一个任务，从目录initPath中查找文件名称以end结尾的文件，返回结果
 * @Author: zhaoyu
 * @Date: 2020/10/24
 */
public class FileSearch implements Runnable{

    private String initPath;
    private String end;
    private List<String> results;

    public FileSearch(String initPath, String end) {
        this.initPath = initPath;
        this.end = end;
        results = new ArrayList<>();
    }

    public List<String> getResults(){
        return results;
    }

    @Override
    public void run() {
        System.out.printf("%s: Starting\n",Thread.currentThread().getName());
        File file = new File(initPath);
        if (file.isDirectory()) {
            directoryProcess(file);
        }
    }
    
    private void directoryProcess(File file){
        File list[]=file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                } 
            }
        }
    }

    private void fileProcess(File file) {
        if (file.getName().endsWith(end)) {
            results.add(file.getAbsolutePath());
        }
    }
}
