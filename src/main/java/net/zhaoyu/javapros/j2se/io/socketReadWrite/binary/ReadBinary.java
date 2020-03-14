package net.zhaoyu.javapros.j2se.io.socketReadWrite.binary;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

//以binary形式从socket中读取数据。
public class ReadBinary {
    public static void main(String[] args) {
        Socket socket=null;
        try {
            socket = new Socket("127.0.0.1", 64783);
            DataInputStream inStream = new DataInputStream
                    (socket.getInputStream());
            //如果读取量大可以使用 BufferedInputStream 包装。
            //DataInputStream inStream = new DataInputStream
            //(new BufferedInputStream(socket.getInputStream()));
            inStream.read();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
