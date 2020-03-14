package net.zhaoyu.javapros.j2se.io.binaryData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
    从文件中读取二进制字节。
 */
public class ReadFile {
    public static void main(String[] args)
    {
        String preffix= ReadFile.class.getResource("/").getPath();
        int b =0;
        FileInputStream in=null;
        try{
            in=new FileInputStream(preffix+"com/zhaoyu/io/binaryData/a.txt" );
        }catch(FileNotFoundException e){
            System.out.println("找不到指定的文件");
            System.exit(-1);
        }

        try{
            long num=0;
            while((b=in.read())!=-1){     //=-1 表示没有读到结尾。
                System.out.print((char)b);
                num++;
            }
            in.close();
            System.out.println();
            System.out.println("共读取"+num+"个字节");
        }catch(IOException e1){
            System.out.println("文件读取错误");
            System.exit(-2);
        }
    }

}
