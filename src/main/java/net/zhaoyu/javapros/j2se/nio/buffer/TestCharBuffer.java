package net.zhaoyu.javapros.j2se.nio.buffer;

import java.nio.CharBuffer;

/**
 * 该类是对CharBuffer的使用，向CharBuffer中写入并读取。
 */
public class TestCharBuffer {
    public static void main(String[] args) {
        // 分配新的int缓冲区，参数为缓冲区容量
        // 新缓冲区的当前位置将为零，其界限(限制位置)将为其容量。它将具有一个底层实现数组，其数组偏移量将为零。
        CharBuffer buffer = CharBuffer.allocate(8);

        /* 标记当前position的位置。
            buffer.mark();
        */

        /*
            将position置零，并清除标志位（mark）
             buffer.rewind();
         */

        /*将position置0，同时将limit设置为capacity的大小，并清除标志位（mark）。
            buffer.clear();
        */

        int j = 2;
        while (String.valueOf(j).length()<=buffer.remaining()){
            // 将给定整数写入此缓冲区的当前位置，当前位置递增
            buffer.put(String.valueOf(j));
            j =j+2;
        }

        // 重设此缓冲区，将limit设置为position，然后将position设置为0,并清除标志位mark,再读写转换时使用。
        buffer.flip();

        // 查看在当前位置和限制位置之间是否有元素
        while (buffer.hasRemaining()) {
            // 读取此缓冲区当前位置的整数，然后当前位置递增
            char c = buffer.get();
            System.out.print(c + "  ");
        }
    }
}
