package net.zhaoyu.javapros.j2se.io.zipStream;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//向一个zip中写入两个名称为f1和f2的文件。
public class ZipWrite {
    public static void main(String[] args) {
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(
                    new FileOutputStream("out.zip"));
            String[] fNames = new String[]{"f1", "f2"};
            for (int i = 0; i < fNames.length; i++) {
                ZipEntry entry = new ZipEntry(fNames[i]);
                FileInputStream fin = new FileInputStream(fNames[i]);

                zipOut.putNextEntry(entry);
                for (int a = fin.read();
                     a != -1; a = fin.read()) {
                    zipOut.write(a);
                }
                fin.close();
            }
            zipOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
