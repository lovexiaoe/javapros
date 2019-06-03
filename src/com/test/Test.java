//用于自己临时调试一些程序。
public class Test {
    public static void main(String[] args) {
        System.out.println("...........");
        Yinshifenjie(8);
       Yinshifenjie(30);
        Yinshifenjie(33);
        Yinshifenjie(11);
        Yinshifenjie(111);
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
}

