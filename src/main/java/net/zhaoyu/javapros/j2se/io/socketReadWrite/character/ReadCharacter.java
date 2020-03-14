package net.zhaoyu.javapros.j2se.io.socketReadWrite.character;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/*
    该程序演示用字符从一个socket中读取数据。
 */
public class ReadCharacter {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 64783);

            InputStreamReader reader = new InputStreamReader
                    (socket.getInputStream());
            BufferedReader bReader = new BufferedReader(reader);
            String msg;
            while ((msg=bReader.readLine())!=null){
                System.out.println(msg);
            }
            bReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
