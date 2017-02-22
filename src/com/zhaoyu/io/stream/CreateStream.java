package com.zhaoyu.io.stream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class CreateStream {
	public static void main(String[] args) throws IOException {
		// 文件输入流
		FileInputStream fin = new FileInputStream("employee.txt");

		// 构建一个数据输入流，读取Double类型。
		DataInputStream din = new DataInputStream(fin);
		double s = din.readDouble();

		// 构建 一个带有缓冲的数据输流
		DataInputStream din2 = new DataInputStream(new BufferedInputStream(fin));

		// java还提供了可以回退的输入流PushbackInputStream,如下可构建一个可回退的输入流
		PushbackInputStream pbin = new PushbackInputStream(new BufferedInputStream(fin));

	}

}
