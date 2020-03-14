package net.zhaoyu.javapros.j2se.threads.ThreadPool.controlFinishATask;

import java.util.concurrent.FutureTask;

public class ResultTask extends FutureTask<String> {

    private final String name;

    public ResultTask(ExecutableTask callable) {
        super(callable);
        this.name=callable.getName();
    }

    //重写的FutureTask的done方法。当任务完成时调用。
    @Override
    protected void done() {
        if (isCancelled()) {
            System.out.printf("%s: Has been cancelled\n",name);
        } else {
            System.out.printf("%s: Has finished\n",name);
        }
    }

}