package net.zhaoyu.javapros.j2se.io.binaryData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//使用字节流向文件中写入数据。
public class WriteToFile {
    public static void main(String[] args) {
        String preffix= WriteToFile.class.getResource("/").getPath();
        int b = 0;
        byte[] array = "i am zhaoyu ,\n我时赵昱".getBytes();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(preffix+"com/zhaoyu/io/binaryData/b.txt" );
            for (int i = 0; i < array.length; i++) {
                out.write(array[i]);
            }
            out.close();
        } catch (FileNotFoundException e2) {
            System.out.println("找不到指定的文件");
            System.exit(-1);
        } catch (IOException e1) {
            System.out.println("文件写入错误");
            System.exit(-1);
        }
        System.out.println("文件已写入");
    }

}
