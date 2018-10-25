package com.zhaoyu.nio.channelmap;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.CRC32;

/**
 * 操作系统可以利用虚拟内存将一个文件或者文件和一部分“映射”到内存中。在性能上内存映射比“带缓冲的输入流”要快些，比“随机访问”和“普通输入流”要快的多。
 * nio实现内存映射的步骤：
 * 1， 从文件或者文件流中获得一个通道（Channel），通道用于磁盘文件的一种抽象，它可以使我们用操作系统的特性操作文件。调用getChannel方法获得通道。
 * 2， 通过调用FileChannel类的map方法从这个通道中获得一个MappedByteBuffer。你可以指定想要映射的文件区域与映射模式，有下面三种模式：
 *  FileChannel.MapMode.READ_ONLY：所产生的缓冲区是只读，任何写入抛出ReadOnlyBufferException。
 *  FileChannel.MapMode.READ_WRITE：产生的缓冲区是可写的，可以有多个修改，一个修改在某个时刻会回写到文件中。
 *  FileChannel.MapMode.PRIVATE：所产生的缓冲区是可写的，任何修改对这个缓冲区是私有的，不会回写到文件中。
 * 3， 有了缓冲区，就可以使用ByteBuffer或者Buffer的方法读写数据了。
 *
 * 缓冲区支持顺序和随机访问
 *
 * @author xiaoE
 *
 */
/**
 * 该类比较了几种对rt.jar进行CRC验证的方法的优劣性，主要说明了内存映射文件的使用。
 *
 * @author xiaoE
 *
 */
public class CRCTest {
	public static void main(String[] args) throws IOException {
		String file = "D:\\javatest\\rttest.jar";

		System.out.println("Input Stream");
		long start = System.currentTimeMillis();
		long crcValue = checkSumInputStream(file);
		long end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) + "milliseconds");

		System.out.println("Buffered Input Stream");
		start = System.currentTimeMillis();
		crcValue = checkSumBufferedInputStream(file);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) + "milliseconds");

		System.out.println("Random access file");
		start = System.currentTimeMillis();
		crcValue = checkSumRandomAccessFile(file);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) + "milliseconds");

		System.out.println("Mapped File");
		start = System.currentTimeMillis();
		crcValue = checkSumMappedFile(file);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) + "milliseconds");
	}

	// 顺序输入流CRC验证
	public static long checkSumInputStream(String fileName) throws IOException {
		InputStream in = new FileInputStream(fileName);
		CRC32 crc = new CRC32();

		int c;
		while ((c = in.read()) != -1) {
			crc.update(c);
		}
		return crc.getValue();
	}

	// 缓冲输入流验证
	public static long checkSumBufferedInputStream(String fileName) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(fileName));
		CRC32 crc = new CRC32();

		int c;
		while ((c = in.read()) != -1) {
			crc.update(c);
		}
		return crc.getValue();
	}

	// 随机访问验证
	public static long checkSumRandomAccessFile(String fileName) throws IOException {
		RandomAccessFile file = new RandomAccessFile(fileName, "r");
		long length = file.length();
		CRC32 crc = new CRC32();

		for (long p = 0; p < length; p++) {
			file.seek(p);
			int c = file.readByte();
			crc.update(c);
		}
		return crc.getValue();
	}

	// 内存映射文件验证
	public static long checkSumMappedFile(String fileName) throws IOException {
		FileInputStream in = new FileInputStream(fileName);
		FileChannel channel = in.getChannel();

		CRC32 crc = new CRC32();
		int length = (int) channel.size();
		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

		for (int i = 0; i < length; i++) {
			int c = buffer.get(i);
			crc.update(c);
		}
		return crc.getValue();
	}

}
