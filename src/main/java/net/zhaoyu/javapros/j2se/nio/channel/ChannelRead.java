package net.zhaoyu.javapros.j2se.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio读取数据时，从IO输入流中获得channel，channel的数据会写入到buffer中，程序再从buffer中读取数据。
 * 流程是： channel(从IO流中开启)-->buffer-->程序(buffer.get())。
 */
public class ChannelRead {
    static public void main( String args[] ){
        String preffix= ChannelRead.class.getResource("/").getPath();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(preffix+"com/zhaoyu/nio/channel/a.txt");

            // 获取通道
            FileChannel fc = fin.getChannel();

            // 创建缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(16);

            while (fc.read(buffer)!=1){
                buffer.flip();

                while (buffer.remaining() > 0) {
                    byte b = buffer.get();
                    System.out.print(((char) b));
                }
                buffer.clear();
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
