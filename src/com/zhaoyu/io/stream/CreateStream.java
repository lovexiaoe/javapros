package com.zhaoyu.io.stream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class CreateStream {
	public static void main(String[] args) throws IOException {
		// �ļ�������
		FileInputStream fin = new FileInputStream("employee.txt");

		// ����һ����������������ȡDouble���͡�
		DataInputStream din = new DataInputStream(fin);
		double s = din.readDouble();

		// ���� һ�����л������������
		DataInputStream din2 = new DataInputStream(new BufferedInputStream(fin));

		// java���ṩ�˿��Ի��˵�������PushbackInputStream,���¿ɹ���һ���ɻ��˵�������
		PushbackInputStream pbin = new PushbackInputStream(new BufferedInputStream(fin));

	}

}
