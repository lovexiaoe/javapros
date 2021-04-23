package net.zhaoyu.javapros.j2se.designpattern.structure.proxy.dongtai.cglib;

/**
 * 模拟一个阿里云发送短信的服务
 */
public class AliSmsService {
    public String send(String msg){
        System.out.println("发送了短信："+msg);
        return msg;
    }
}
