package net.zhaoyu.javapros.j2se.io.socketReadWrite.character;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

//演示向一个socket中写入字符串。
public class WriteCharacter {
    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 64783);
            PrintWriter pWriter = new PrintWriter
                    (socket.getOutputStream());
            pWriter.println("Dad, we won the game.");
            pWriter.flush();
            pWriter.close();
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
