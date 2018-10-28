package com.zhaoyu.io.socketReadWrite.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//使用binary向socket中写入数据。
public class WriteBinary {
    public static void main(String[] args) {
        Socket socket=null;
        try {
            byte positions[] = new byte[10];
            socket = new Socket("127.0.0.1", 64783);
            DataOutputStream outStream = new DataOutputStream
                    (socket.getOutputStream());
            //如果数据量大可以使用BufferedOutputStream包装。
            /*DataOutputStream outStream = new DataOutputStream
                    (new BufferedOutputStream(socket.getOutputStream()));*/
            outStream.write(positions, 0, 10);
            outStream.close();
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
