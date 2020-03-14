package net.zhaoyu.javapros.j2se.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

//spring 的默认解析json框架为jackson。下面代码需要依赖jackson包。
public class jacksonTest {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        //对象转换为Json
        User user = new User("张三", 23, "深圳市");
        String userJsonStr = objectMapper.writeValueAsString(user);

        //JSON转java对象：
        String json = "{\"name\":\"张三\",\"age\":23,\"address\":\"深圳市\"}";
        User zhang = objectMapper.readValue(json, User.class);

    }
}
