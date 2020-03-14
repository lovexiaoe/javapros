package net.zhaoyu.javapros.j2se.threads.forkJoin.joinResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class LineTask extends RecursiveTask<Integer> {


    private String line[];

    /**
     * Range of positions the task has to process
     */
    private int start, end;

    /**
     * Word we are looking for
     */
    private String word;

    /**
     * Constructor
     */
    public LineTask(String line[], int start, int end, String word) {
        this.line=line;
        this.start=start;
        this.end=end;
        this.word=word;
    }

    /**
     * 一行中少于100个单次则计算，大于则分为更小的任务。
     */
    @Override
    protected Integer compute() {
        Integer result=null;
        if (end-start<100) {
            result=count(line, start, end, word);
        } else {
            int mid=(start+end)/2;
            LineTask task1=new LineTask(line, start, mid, word);
            LineTask task2=new LineTask(line, mid, end, word);
            invokeAll(task1, task2);
            try {
                result=groupResults(task1.get(),task2.get());
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

    /**
     * 计算一行中某个单次出现的频率
     */
    private Integer count(String[] line, int start, int end, String word) {
        int counter;
        counter=0;
        for (int i=start; i<end; i++){
            if (line[i].equals(word)){
                counter++;
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return counter;
    }



}