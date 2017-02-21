package com.zhaoyu.io.binaryinputoutput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ���ԶԹ̶����������ͽ���д��Ͷ�ȡ��
 *
 * DataOutput�ӿڶ��������������Զ����Ƹ�ʽд���飬�ַ���boolean���ַ����ķ�����
 * writeChars
 * writeByte
 * writeInt
 * ����
 * writeChar
 * writeUTF
 * �磺writeInt���ǽ�һ������д��Ϊ4�ֽڵĶ���������ֵ�����������ж���λ��writeDouble���ǽ�һ��doubleֵд��Ϊ8�ֽڵĶ���������ֵ�������������ز���ʱ������Ҫ���н������ȱ���Ϊ�ַ�����Ч�ʸߡ�
 * writeUTF����ʹ���޶����8λUnicodeת����ʽд���ַ�����ֻ����д������java��������ַ���ʱ��ʹ���������������������Ӧ��ʹ��writechars������
 * 
 * ��DataInput�ӿ��ж����˶������ݵķ�����
 * readInt
 * readDouble
 * ����
 * readChar
 * readUTF
 * 
 * DataInputStreamʵ����DataInput�ӿڣ�DataOutputStreamʵ����DataOutput�ӿڡ�
 * 
 *
 * @author xiaoE
 *
 */
public class DataStream {
	public static void main(String[] args) {
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream("employee1.dat"));
			out.writeBoolean(false);
			out.writeChar('a');

			out.close();

			in = new DataInputStream(new FileInputStream("employee1.dat"));
			// get boolean
			Boolean b = in.readBoolean();
			System.out.println(b);
			// get char
			System.out.println(in.readChar());

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
