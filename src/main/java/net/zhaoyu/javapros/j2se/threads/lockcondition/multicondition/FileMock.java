package net.zhaoyu.javapros.j2se.threads.lockcondition.multicondition;

/**
 * @Description: 该类用于模拟一个文本文件
 * @Author: zhaoyu
 * @Date: 2020/10/21
 */
public class FileMock {
    //文件内容
    private String[] content;
    //正在处理的行
    private int index;

    /**
     * 构造一个文本文件
     * @param size 文件的行数
     * @param length 每行的长度
     */
    public FileMock(int size, int length){
        content = new String[size];
        for (int i = 0; i< size; i++){
            StringBuilder buffer = new StringBuilder(length);
            for (int j = 0; j < length; j++){
                int randomCharacter= (int)Math.random()*255;
                buffer.append((char)randomCharacter);
            }
            content[i] = buffer.toString();
        }
        index=0;
    }

    /**
     * 是否还有更多行待处理。
     * @return
     */
    public boolean hasMoreLines(){
        return index <content.length;
    }

    /**
     * 获取需要处理的下一行，如果没有，返回null
     * @return
     */
    public String getLine(){
        if (this.hasMoreLines()) {
            return content[index++];
        }
        return null;
    }
}
