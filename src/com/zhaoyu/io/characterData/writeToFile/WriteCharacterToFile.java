package com.zhaoyu.io.characterData.writeToFile;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteCharacterToFile {
    public static void main(String[] args) {
        fileWriterWrite();
        printWriterWrite();
    }

    //fileWriter 写入字符。
    private  static void fileWriterWrite(){
        FileWriter fWriter =null;
        String preffix= WriteCharacterToFile.class.getResource("/").getPath();
        try {
            fWriter = new
                    FileWriter(preffix+"com/zhaoyu/io/characterData/writeToFile/a.txt");
            //write方法不会换行。
            fWriter.write("This is the coach list.");
            fWriter.write("新的一行文字。");
            fWriter.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("未找到文件");
        }
        catch (IOException e)
        {
            System.out.println("读取文件错误");
        }

    }


    private  static void printWriterWrite(){
        String preffix= WriteCharacterToFile.class.getResource("/").getPath();
        try {
            PrintWriter pWriter = new PrintWriter
                    (new FileWriter(preffix+"com/zhaoyu/io/characterData/writeToFile/b.txt"));
            pWriter.println("A huge line of text");
            pWriter.println("这是新的一行。");
            pWriter.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("未找到文件");
        }
        catch (IOException e)
        {
            System.out.println("读取文件错误");
        }

    }
}
