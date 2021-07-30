package net.zhaoyu.javapros.test;


//用于自己临时调试一些程序。
public class Test {
    public static final int RESIZE_STAMP_BITS=16;
    public static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
    public static void main(String[] args) {
        for (int n = 1;n<1024 ; n*=2) {
            int rs = resizeStamp(n);
            int left16=rs << RESIZE_STAMP_SHIFT;
            int sc1 = (rs << RESIZE_STAMP_SHIFT) + 2;

            int sc3 = sc1 >>> RESIZE_STAMP_SHIFT;
            System.out.println("n:"+n+"    rs:"+rs+"    left16:"+left16+"   sc1:"+sc1+"     sc3:"+sc3);
            System.out.println("n:"+Integer.toBinaryString(n)+"    rs:"+Integer.toBinaryString(rs)+
                    "    left16:"+Integer.toBinaryString(left16)+"   sc1:"+Integer.toBinaryString(sc1)
                    +"     sc3:"+Integer.toBinaryString(sc3));
        }
        System.out.println("+2结果无符号右移："+(-2145452030>>>RESIZE_STAMP_SHIFT));
        System.out.println(Integer.toString(15, 2));
        System.out.println(Integer.toString(-15, 2));
        System.out.println(Integer.toUnsignedString(-15,2));
        System.out.println(Integer.valueOf("111110000000000000000",2)+"是否等于"+"-2145452032");
        System.out.println(Integer.valueOf("1111111111000010000000000000001",2)+"是否等于"+"2145452032");
    }

    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
}

