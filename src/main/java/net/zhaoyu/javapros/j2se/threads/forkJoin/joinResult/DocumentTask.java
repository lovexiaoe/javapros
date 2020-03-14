package net.zhaoyu.javapros.j2se.threads.forkJoin.joinResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;


public class DocumentTask extends RecursiveTask<Integer> {

    private static final long serialVersionUID = 1L;


    private String document[][];


    private int start, end;


    private String word;


    public DocumentTask (String document[][], int start, int end, String word){
        this.document=document;
        this.start=start;
        this.end=end;
        this.word=word;
    }

    /**
     * 任务需要处理的行数超过10行，分解为更小的文件任务
     * 否则，使用行任务处理每行。
     */
    @Override
    protected Integer compute() {
        Integer result=null;
        if (end-start<10){
            result=processLines(document, start,end,word);
        } else {
            int mid=(start+end)/2;
            DocumentTask task1=new DocumentTask(document,start,mid,word);
            DocumentTask task2=new DocumentTask(document,mid,end,word);
            invokeAll(task1,task2);
            try {
                result=groupResults(task1.get(),task2.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 使用 LineTask 处理块中的每行。
     * @return
     */
    private Integer processLines(String[][] document, int start, int end,
                                 String word) {
        List<LineTask> tasks=new ArrayList<LineTask>();

        for (int i=start; i<end; i++){
            LineTask task=new LineTask(document[i], 0, document[i].length, word);
            tasks.add(task);
        }
        invokeAll(tasks);

        int result=0;
        for (int i=0; i<tasks.size(); i++) {
            LineTask task=tasks.get(i);
            try {
                result=result+task.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 合并结果
     */
    private Integer groupResults(Integer number1, Integer number2) {
        Integer result;

        result=number1+number2;
        return result;
    }


}