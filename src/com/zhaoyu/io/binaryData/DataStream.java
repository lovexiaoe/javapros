package com.zhaoyu.io.binaryData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 可以对固定的数据类型进行写入和读取。
 *
 * DataOutput接口定义了下面用于以二进制格式写数组，字符，boolean和字符串的方法：
 * writeChars
 * writeByte
 * writeInt
 * ……
 * writeChar
 * writeUTF
 * 如：writeInt总是将一个整数写出为4字节的二进制数量值。而不管它有多少位，writeDouble总是将一个double值写出为8字节的二进制数量值。这样在做读回操作时，不需要进行解析，比保存为字符串的效率高。
 * writeUTF方法使用修订版的8位Unicode转换格式写出字符串。只有在写出用于java虚拟机的字符串时才使用它。对于其它情况，都应该使用writechars方法。
 * 
 * 在DataInput接口中定义了读回数据的方法。
 * readInt
 * readDouble
 * ……
 * readChar
 * readUTF
 * 
 * DataInputStream实现了DataInput接口，DataOutputStream实现了DataOutput接口。
 *
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
			//如果有大量的数据写入，那么使用BufferedOutputStream代替FileOutputStream
			out = new DataOutputStream(new FileOutputStream("employee1.dat"));
			out.writeBoolean(false);
			out.writeChar('a');
			out.writeBoolean(true);

			out.close();

			// 如果有大量的数据读取，那么使用BufferedInputStream代替FileInputStream
			in = new DataInputStream(new FileInputStream("employee1.dat"));
			//这里读取的顺序是，必须和写入的顺序是一致的，不然会出现不可预期的情况。

			// get boolean
			Boolean b = in.readBoolean();
			System.out.println(b);

			// get char
			char c=in.readChar() ;
			System.out.println(c);

			// get boolean
			Boolean b2 = in.readBoolean();
			System.out.println(b2);

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
