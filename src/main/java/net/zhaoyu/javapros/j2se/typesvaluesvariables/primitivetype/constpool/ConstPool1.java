package net.zhaoyu.javapros.j2se.typesvaluesvariables.primitivetype.constpool;

/**
 * Java 基本类型的包装类的大部分都实现了常量池技术，即 Byte,Short,Integer,Long,Character,Boolean；前面 4 种包装类默认创建了
 * 数值[-128，127] 的相应类型的缓存数据，Character 创建了数值在[0,127]范围的缓存数据，Boolean 直接返回 True Or False。
 * 如果超出对应范围仍然会去创建新的对象。 为啥把缓存设置为[-128，127]区间？（参见 issue/461）性能和资源之间的权衡。
 */
public class ConstPool1 {
    public static void main(String[] args) {
        Character c1= (char) 40;
        Character c2= 40;
        System.out.println(c1==c2);//true Character的0-127使用常量池。

        Integer i1 = 33;
        Integer i2 = 33;
        System.out.println(i1 == i2);// true [-128，127]使用常量池
        Integer i11 = 333;
        Integer i22 = 333;
        System.out.println(i11 == i22);// false 超出127，新建对象。
        Double i3 = 1.2;
        Double i4 = 1.2;
        System.out.println(i3 == i4);// false double新建对象。

        Integer i5 = 40;
        Integer i6 = new Integer(40);
        System.out.println(i5 == i6);//输出 false,new 新建了对象。


    }
}
