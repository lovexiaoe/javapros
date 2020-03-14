package net.zhaoyu.javapros.j2se.io.characterData.readFromFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCharacterFromFile {
    public static void main(String[] args) {
        fileReaderRead();
        bufferedReaderRead();
    }

    //使用FileReader读取文件
    private static void fileReaderRead() {
        FileReader fr=null;
        int c=0;
        try
        {
            //classpath的绝对路径。
            String preffix= ReadCharacterFromFile.class.getResource("/").getPath();
            fr=new FileReader(preffix+"com/zhaoyu/io/characterData/readFromFile/a.txt");
            int in=0;
            while ((c=fr.read())!=-1)
            {
                //java
                System.out.print((char)c);
            }
            fr.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("未找到文件");
        }
        catch (IOException e)
        {
            System.out.println("读取文件错误");
        }
    }

    //使用BufferedReader读取文件，可以整行读取。提高效率。
    private static void bufferedReaderRead() {
        BufferedReader bReader = null;
        String preffix= ReadCharacterFromFile.class.getResource("/").getPath();
        try {
            bReader = new BufferedReader
                    (new FileReader(preffix+"com/zhaoyu/io/characterData/readFromFile/a.txt"));

            String lineContents;
            // 读取的行最后没有换行符
            while ((lineContents = bReader.readLine())
                    != null) {
                System.out.println(lineContents);
            }

            bReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("未找到文件");
        }catch (IOException e)
        {
            System.out.println("读取文件错误");
        }
    }

}
