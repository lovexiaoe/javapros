package net.zhaoyu.javapros.j2se.nio.selecter.multiThreadServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.locks.LockSupport;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost", 8000));
            writer = new PrintWriter(client.getOutputStream(), true);
            writer.print("Hello1!");
            writer.flush();
            sendHello2(writer);
            reader = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            System.out.println("from server: " + reader.readLine());
        } catch (Exception e) {
        } finally {
            reader.close();
            writer.close();
            client.close();
        }
    }

    //模仿网络延迟发送数据。
    public static void sendHello2(PrintWriter writer) {
        int sleep_time = 1000 * 1000 * 1000;
        writer.print("H");
        LockSupport.parkNanos(sleep_time);
        writer.print("e");
        LockSupport.parkNanos(sleep_time);
        writer.print("l");
        LockSupport.parkNanos(sleep_time);
        writer.print("l");
        LockSupport.parkNanos(sleep_time);
        writer.print("o");
        LockSupport.parkNanos(sleep_time);
        writer.print("2");
        LockSupport.parkNanos(sleep_time);
        writer.print("!");
        LockSupport.parkNanos(sleep_time);
        writer.println();
        writer.flush();
    }
}
