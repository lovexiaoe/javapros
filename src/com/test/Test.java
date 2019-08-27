//用于自己临时调试一些程序。
public class Test {
    public static void main(String[] args) {
        printArray(devide2part(63,4));
        printArray(devide2part(123512386,6));
        printArray(devide2part(365,3));
        printArray(devide2part(50,6));
        printArray(devide2part(49,10));
    }

    private static void testReplace() {
        replace("123456789");
        replace("123412356789");
        replace("123412321321356789");
        replace("12345612321321321789");
    }

    /**
     * @Description: 传入一个数，如果小于50按50计算。将0到该数的ceil（按照两位有效整数取），较均匀地划分为level段。
     *              如：365可以分为[0,90,180,270,370]；
     * @param max  最大值。
     * @param level 划分所得数组的长度，小于3则按3计算。
     * @Author: zhaoyu
     * @Date 2019/8/22
     */
    static long[] devide2part(double max,int level){
        if (level<3){
            level=3;
        }
        if (max<50){
            max=50;
        }
        long[] arr;
        arr=new long[level];
        arr[0]=0;
        //计算整数长度-1
        int len = (int)(Math.floor(Math.log10(max)));
        long divide=1;
        System.out.println("数长："+len);
        //得到10的len-1次方。
        for (int i = 1; i < len; i++) {
            divide=divide*10;
        }
        //得到两位整数的ceil，如max=175得到ceil为180，shortCeil为18。between为18/4*10
        long shortCeil= (long) (Math.ceil(max/(double)divide));
        long ceil= (long) (Math.ceil(max/(double)divide)*divide);
        long between=shortCeil/(level-1)*divide;
        //组装数组并返回。
        for (int i = 1; i <level-1 ; i++) {
            arr[i]=arr[i-1]+between;
        }
        arr[level-1]=ceil;
        return arr;
    }
    static void printArray(long[] arr){
        System.out.printf("[");
        for (int i = 0; i <arr.length ; i++) {
            System.out.printf(arr[i]+",");
        }
        System.out.println("]");
    }
    public static void Yinshifenjie(int m){
        int k=2;
        while(m>k){
            if (m%k==0){
                System.out.printf(k+"*");
                m=m/k;
            }else {
                k++;
            }
        }
        System.out.println(m);
    }
    public static void replace(String s){
        System.out.println(s.replaceAll("^(\\d+)\\d{4}(\\d{4})$","$1****$2"));
    }
}

