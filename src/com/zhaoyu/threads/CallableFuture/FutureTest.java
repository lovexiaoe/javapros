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
 * ������һ��Ŀ¼�в��Ұ���ĳ�ؼ��ֵ��ļ����������Ŀ¼��Ƕ��Ŀ¼��
 * ��ÿ��Ŀ¼���½�һ���µ�Callable�̣߳����������̵߳�Future������һ���б��У��������յ��б���Ԫ�ص��ܺ͡�
 *
 * @author xiaoE
 *
 */
public class FutureTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("�������·��(�磺c:/mydir):");

		String directory = sc.nextLine();
		System.out.print("����ؼ��֣�");
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
			// �������ļ���·�����ݹ�ִ��������
			if (file.isDirectory()) {
				MatchCounter counter = new MatchCounter(file, keyword);
				/**
				 * FutureTask��һ���ɽ�Callableת����Future��Runnable�İ�װ��,��ͬʱʵ�ֶ��ߵĽӿڡ�����
				 */
				FutureTask<Integer> task = new FutureTask<Integer>(counter);
				results.add(task);
				// ---taskȡ��Runnable
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
				// ---taskȡ��Future
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
