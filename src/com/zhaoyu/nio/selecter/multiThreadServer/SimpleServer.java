package com.zhaoyu.nio.selecter.multiThreadServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 这是一个普通的多线程服务器，没一个客户端的请求都会启动一个线程，用于阻塞处理客户端的输入。
 * 这种阻塞IO的服务器，由于客户端网络延迟等引起的问题，服务器端线程可能会占用很长的时间。如果客户端数量巨大，
 * 可能会消耗大量的系统资源，
 *
 * 以餐厅打饭为例，VIP餐厅中，一个服务员为一个顾客打饭，这个顾客可能边想（想的过程相当于网络等待）边打（打的过程才是
 * 实际需要处理的动作。）实际上这种效率不高，10个服务员只能同时服务10个顾客。对应于NIO,NIO就是快餐店，服务员可以同时
 * 服务多个顾客，客户想（网络等待）的时间，服务员可以给其他客户打，那么10个服务员可用同时服务100个顾客。
 */
public class SimpleServer {
    private static ExecutorService tp = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {

        ServerSocket echoServer = null;
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(8000);
        } catch (IOException e) {
            System.out.println(e);
        }
        while (true) {
            try {
                clientSocket = echoServer.accept();   //启动一个socket链接。
                System.out.println(clientSocket.getRemoteSocketAddress()
                        + " connect!");
                tp.execute(new HandleMsg(clientSocket));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

class HandleMsg implements Runnable {
    private Socket clientSocket;

    public HandleMsg(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    //接受客户端发送的消息，并直接返回给客户端。
    public void run() {
        BufferedReader is = null;
        PrintWriter os = null;
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintWriter(clientSocket.getOutputStream(), true);
            // 从InputStream当中读取客户端所发送的数据
            String inputLine = null;
            long b = System.currentTimeMillis();
            //这里读取客户端数据是阻塞执行，相对于nio来讲，当客户端两巨大时，会消耗大量的系统资源，
            while ((inputLine = is.readLine()) != null) {
                os.println(inputLine);
            }
            long e = System.currentTimeMillis();
            System.out.println("spend:" + (e - b) + " ms ");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
                Socket java.net.SocketException: Connection reset 错误，
                当socket 一端未正常关闭连接时，另一端还再都或者写，就会报这个错误。可以使用InputStream.available判断有效输入。
                如
                 while ((inputLine = is.readLine()) != null && is.available()!=0) {
                    os.println(inputLine);
                }
             */
        }
    }
}