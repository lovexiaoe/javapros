package net.zhaoyu.javapros.j2se.threads.concurrentdesign.forkjoin;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * forkjoin任务，对一个数组的所有元素加1，在元素很多时，如果元素多余1000，则分为两组任务，进行forkjoin。
 */
public class TaskFJ extends RecursiveAction {

	private final int array[];
	private final int start, end;
	
	public TaskFJ(int array[], int start, int end) {
		this.array=array;
		this.start=start;
		this.end=end;
	}
	
	@Override
	protected void compute() {
		//如果元素个数大于1000，forkjoin分组处理
		if (end-start>1000) {
			int mid=(start+end)/2;
			TaskFJ task1=new TaskFJ(array,start,mid);
			TaskFJ task2=new TaskFJ(array,mid,end);
			task1.fork();
			task2.fork();
			task1.join();
			task2.join();
		} else {
			for (int i=start; i<end; i++) {
				array[i]++;
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
