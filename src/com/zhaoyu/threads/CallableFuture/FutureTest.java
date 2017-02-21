package com.zhaoyu.threads.CallableFuture;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 *
 * 下例在一个目录中查找包含某关键字的文件个数，这个目录是嵌套目录。
 * 在每个目录中新建一个新的Callable线程，并将所有线程的Future保存在一个列表中，计算最终的列表中元素的总和。
 *
 * @author xiaoE
 *
 */
public class FutureTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("输入绝对路径(如：c:/mydir):");

		String directory = sc.nextLine();
		System.out.print("输入关键字：");
		String keyword = sc.nextLine();

		MatchCounter counter = new MatchCounter(new File(directory), keyword);
		FutureTask<Integer> task = new FutureTask<Integer>(counter);
		Thread t1 = new Thread(task);
		t1.start();

		try {
			System.out.println(task.get() + "matching files");
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}

class MatchCounter implements Callable<Integer> {
	private File directory;
	private String keyword;
	private int count;

	// constructor
	public MatchCounter(File directory, String keyword) {
		this.directory = directory;
		this.keyword = keyword;
	}

	@Override
	public Integer call() throws InterruptedException {
		count = 0;

		File[] files = directory.listFiles();
		ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();

		for (File file : files) {
			// 如果这个文件是路径，递归执行搜索。
			if (file.isDirectory()) {
				MatchCounter counter = new MatchCounter(file, keyword);
				/**
				 * FutureTask是一个可将Callable转换成Future和Runnable的包装器,它同时实现二者的接口。例如
				 */
				FutureTask<Integer> task = new FutureTask<Integer>(counter);
				results.add(task);
				// ---task取得Runnable
				Thread t = new Thread(task);
				t.start();
			} else {
				if (search(file)) {
					count++;
				}
			}
		}
		for (Future<Integer> result : results) {
			try {
				// ---task取得Future
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
