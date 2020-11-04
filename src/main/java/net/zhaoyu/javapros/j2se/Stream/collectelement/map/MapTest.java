package net.zhaoyu.javapros.j2se.Stream.collectelement.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: map使用stream实例
 * @Author: zhaoyu
 * @Date: 2020/11/4
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, Integer> ageOfFriends = new HashMap<>();
        ageOfFriends.put("张三",22);
        ageOfFriends.put("李四",24);
        ageOfFriends.put("王麻子", 88);

        /**
         * computeIfPresent在key值存在时，计算值。并将key的value设置为该值。
         */
        Integer age = ageOfFriends.computeIfPresent("张三", (k, v) -> new Integer(18));
        System.out.println(ageOfFriends.get("张三"));
        /**
         * computeIfAbsent在key值不存在时，计算值，并插入到map中。
         */
        ageOfFriends.computeIfAbsent("赵六", k -> new Integer(18));
        System.out.println(ageOfFriends.get("赵六"));
        /**
         * 删除某个值
         */
        ageOfFriends.remove("张三", 18);
    }
}
