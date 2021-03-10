package net.zhaoyu.javapros.j2se.basic.string;

import java.util.StringTokenizer;

/**
 * @Description: StringTokenizer用于分隔字符串。
 * @Author: zhaoyu
 * @Date: 2020/12/25
 */
public class StringTokenizerTest {
    public static void main(String[] args) {
        for (StringTokenizer stringTokenizer = new StringTokenizer("123|魔法￥|@#","|"); stringTokenizer.hasMoreTokens(); ) {
            String s = stringTokenizer.nextToken();
            System.out.println(s);
        }
    }
}
