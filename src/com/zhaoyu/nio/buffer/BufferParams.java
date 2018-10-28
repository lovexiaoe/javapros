package com.zhaoyu.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 该程序用于理解buffer 的3个参数之间的关系。
 * 0<=position<=limit<=capacity。
 */
public class BufferParams {
    public static void main(String[] args) {
        ByteBuffer b = ByteBuffer.allocate(15); // 15个字节大小的缓冲区
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        //limit=15 capacity=15 position=0
        for (int i = 0; i < 10; i++) {
            // 存入10个字节数据
            b.put((byte) i);
        }
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        //limit=15 capacity=15 position=10
        b.flip(); // 重设此缓冲区，将limit设置为position，然后将position设置为0,并清除标志位mark,再读写转换时使用。
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        //limit=10 capacity=15 position=0
        for (int i = 0; i < 5; i++) {
            System.out.print(b.get());
        }
        System.out.println();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        //limit=10 capacity=15 position=5
        b.flip(); //读写转换 ，这里由读转换为写，那么0-5的位置为有效位置。
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        //limit=5 capacity=15 position=0

        b.clear(); //清除buffer，position置0，limit置为capacity，还原到最开始创建的状态，

    }
}
