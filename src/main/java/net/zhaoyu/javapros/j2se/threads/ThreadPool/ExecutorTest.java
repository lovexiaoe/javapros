package net.zhaoyu.javapros.j2se.threads.ThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建大量线程会降低性能，甚至使虚拟机崩溃。
 * 使用线程池，可以减少并发程序的数目，应该使用一个线程数"固定的"线程池以限制并发程序的总数。
 * 执行器（Executor）类有许多静态工厂方法来构建线程池，
 *
 * newCachedThreadPool 必要时创建新线程；空闲线程会被保留60秒。
 * newFixedThreadPool 该池包含固定数量的线程；空闲线程会一起保留。
 * newSingleThreadExecutor 只有一个线程的"池"。该线程顺序执行每一个提交的任务。
 * newScheduledThreadPool 用于预定执行而构建的固定线程池，替代java.consumers.Timer
 * newSingleThreadScheduledExecutor 用于预定执行面构建的单线程"池"。
 *
 * 前3个方法返回实现了ExecutorService接口的ThreadPoolExecutor类的对象。
 * ScheduledExecutorService接口具有为预定执行（scheduled execution）或重复执行任务而设计的类。
 * 它是一种允许使用线程池机制的java.consumers.Timer的泛化。Executor类的newScheduledThreadPool和
 * newSingleThreadScheduledExecutor方法将返回实现了ScheduledExecutorService的对象。
 *
 * 可以使用下列方法将一个Runnable对象或者Callable对象提交给ExecutorService:
 * Future<?> submit(Runnable task);
 * Future<T> submit(Runnable task,T result);
 * Future<T> submit(Callable<T> task);
 * 第一个方法返回一个Future<?>。可以使用这样一个对象调用isDone,cancel或isCancelled。get方法在完成时返回null。
 * 第二个方法，Future的get方法在完成时返回指定的result对象。
 * 第三个方法，Future将在计算结果准备好的时候得到它。
 *
 * 线程池的关闭：调用shutdown和shutdownNow方法关闭线程池。
 *
 * @author xiaoE
 *
 */

/**
 * FutureTest（查找一个目录中包含某关键字的文件个数） 使用线程池实现。
 *
 * @author xiaoE
 *
 */
public class ExecutorTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("输入绝对路径(如：c:/mydir):");

		String directory = sc.nextLine();
		System.out.print("输入关键字：");
		String keyword = sc.nextLine();

		ExecutorService pool = Executors.newCachedThreadPool();
		MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
		Future<Integer> result = pool.submit(counter);

		try {
			System.out.println(result.get() + "matching files");
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		pool.shutdown();

		// Executor不能获取线程池最大线程数，需要将其转换成ThreadPoolExecutor。
		int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
		System.out.println("large pool size=" + largestPoolSize);
	}
}

class MatchCounter implements Callable<Integer> {
	private File directory;
	private String keyword;
	private ExecutorService pool;
	private int count;

	// constructor
	public MatchCounter(File directory, String keyword, ExecutorService pool) {
		this.directory = directory;
		this.keyword = keyword;
		this.pool = pool;
	}

	@Override
	public Integer call() throws InterruptedException {
		count = 0;

		File[] files = directory.listFiles();
		ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();

		for (File file : files) {
			// 如果这个文件是路径，递归执行搜索。
			if (file.isDirectory()) {
				// 将新建任务添加到线程池。
				MatchCounter counter = new MatchCounter(file, keyword, pool);
				Future<Integer> result = pool.submit(counter);
				results.add(result);
			} else {
				if (search(file)) {
					count++;
				}
			}
		}
		for (Future<Integer> result : results) {
			try {
				count += result.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public boolean search(File file) {
		try {
			Scanner in = new Scanner(new FileInputStream(file));
			boolean found = false;
			while (!found && in.hasNextLine()) {
				String line = in.nextLine();
				if (line.contains(keyword)) {
					found = true;
				}
			}
			in.close();
			return found;
		} catch (Exception e) {
			return false;
		}
	}
}
