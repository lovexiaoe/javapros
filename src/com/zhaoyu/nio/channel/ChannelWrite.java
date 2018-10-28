package com.zhaoyu.nio.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * nio写入数据时，先将数据通过buffer的put方法写入到buffer中，buffer再write到channel（从IO输出流中开启），
 * 流程如下：程序(buffer.put)-->buffer--channel(从IO输出流中开启)
 */
public class ChannelWrite {
    static private final String text="123123333123123\n" +
            "asdfweonfoasdjfowiejofj\n" +
            "异步确保型\n" +
            "最大努力通知型几种。 由于支付宝整个架构是SOA架构，\n" +
            "因此传统单机环境下数据库的ACID事务满足了分布式环境下的业务需要，\n" +
            "以上几种事务类似就是针对分布式环境下业务需要设定的。";
    static private final byte message[] = text.getBytes();

    static public void main(String args[]) {
        String preffix = ChannelRead.class.getResource("/").getPath();
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(preffix + "com/zhaoyu/nio/channel/b.txt");
            FileChannel fc = fout.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(16);
            int i=0;

            while (i<message.length){
                while (i<message.length&&buffer.hasRemaining()){
                    buffer.put(message[i]);
                    ++i;
                }
                buffer.flip();
                fc.write(buffer);
                buffer.clear();
            }
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
