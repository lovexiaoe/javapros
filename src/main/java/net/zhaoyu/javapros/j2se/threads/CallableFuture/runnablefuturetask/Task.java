package net.zhaoyu.javapros.j2se.threads.CallableFuture.runnablefuturetask;

import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * @Description: 定义futuretask。
 * @Author: zhaoyu
 * @Date: 2020/10/24
 */
public class Task extends FutureTask<List<String>> {
    private FileSearch fileSearch;

    public Task(Runnable runnable, List<String> result) {
        super(runnable, result);
        this.fileSearch=(FileSearch) runnable;
    }


    //在运行Runnable对象时，需要重写set方法，将Futuretask的返回值设置为v。这样在FutureTask.get方法才能得到结果，并且不能通过Future的
    //get方法来获取结果。
    @Override
    protected void set(List<String> v) {
        v=fileSearch.getResults();
        super.set(v);
    }
}
