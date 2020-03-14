package net.zhaoyu.javapros.j2se.io.zipStream;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
    从zip中读取文件，并写成新的文件，还没有测试，
 */
public class ZipRead {
    public static void main(String[] args) {
        ZipInputStream zipIn = null;
        try {
            zipIn = new ZipInputStream(
                    new FileInputStream("out.zip"));
            ZipEntry entry;
            int i=0;
            while ((entry=zipIn.getNextEntry())!=null){
                System.out.println(entry.getName());
                DataOutputStream dos=new DataOutputStream(new FileOutputStream(entry.getName()));
                int b;
                while((b=zipIn.read())>-1)
                {//通过read方法来读取文件内容
                    dos.write(b);
                }
                dos.flush();
                dos.close();
                zipIn.closeEntry();
            }
            zipIn.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
