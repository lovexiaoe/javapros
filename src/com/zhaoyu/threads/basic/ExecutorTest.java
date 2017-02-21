package com.zhaoyu.threads.basic;

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
 * ���������̻߳ή�����ܣ�����ʹ�����������
 * ʹ���̳߳أ����Լ��ٲ����������Ŀ��Ӧ��ʹ��һ���߳���"�̶���"�̳߳������Ʋ��������������
 * ִ������Executor��������ྲ̬���������������̳߳أ�
 *
 * newCachedThreadPool ��Ҫʱ�������̣߳������̻߳ᱻ����60�롣
 * newFixedThreadPool �óذ����̶��������̣߳������̻߳�һ������
 * newSingleThreadExecutor ֻ��һ���̵߳�"��"�����߳�˳��ִ��ÿһ���ύ������
 * newScheduledThreadPool ����Ԥ��ִ�ж������Ĺ̶��̳߳أ����java.util.Timer
 * newSingleThreadScheduledExecutor ����Ԥ��ִ���湹���ĵ��߳�"��"��
 *
 * ǰ3����������ʵ����ExecutorService�ӿڵ�ThreadPoolExecutor��Ķ���
 * ScheduledExecutorService�ӿھ���ΪԤ��ִ�У�scheduled execution�����ظ�ִ���������Ƶ��ࡣ
 * ����һ������ʹ���̳߳ػ��Ƶ�java.util.Timer�ķ�����Executor���newScheduledThreadPool��
 * newSingleThreadScheduledExecutor����������ʵ����ScheduledExecutorService�Ķ���
 *
 * ����ʹ�����з�����һ��Runnable�������Callable�����ύ��ExecutorService:
 * Future<?> submit(Runnable task);
 * Future<T> submit(Runnable task,T result);
 * Future<T> submit(Callable<T> task);
 * ��һ����������һ��Future<?>������ʹ������һ���������isDone,cancel��isCancelled��get���������ʱ����null��
 * �ڶ���������Future��get���������ʱ����ָ����result����
 * ������������Future���ڼ�����׼���õ�ʱ��õ�����
 *
 * �̳߳صĹرգ�����shutdown��shutdownNow�����ر��̳߳ء�
 *
 * @author xiaoE
 *
 */

/**
 * FutureTest������һ��Ŀ¼�а���ĳ�ؼ��ֵ��ļ������� ʹ���̳߳�ʵ�֡�
 *
 * @author xiaoE
 *
 */
public class ExecutorTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("�������·��(�磺c:/mydir):");

		String directory = sc.nextLine();
		System.out.print("����ؼ��֣�");
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

		// Executor���ܻ�ȡ�̳߳�����߳�������Ҫ����ת����ThreadPoolExecutor��
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
			// �������ļ���·�����ݹ�ִ��������
			if (file.isDirectory()) {
				// ���½�������ӵ��̳߳ء�
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
