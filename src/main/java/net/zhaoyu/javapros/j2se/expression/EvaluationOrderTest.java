package net.zhaoyu.javapros.j2se.expression;

/**
 * java保证表达式的操作的操作数按照特定的顺序求值，即从左到右。
 *
 * 1.首先计算左操作数。
 * 2.在操作执行之前，计算操作数。
 * 3.求值遵从括号和优先级。
 * 4.参数列表从左到有求值。
 */
public class EvaluationOrderTest {
    public static void main(String[] args) {
        int i=2;
        int j = (i = 3) * i; //先计算左操作，如果做操作数为赋值表达式，执行并保存。
        System.out.println(j); //9

        int a = 9;
        a += (a = 3); // a=a+(a=3)=9+3=12
        System.out.println(a);

        //表达式计算过程中出现异常，左操作数出现异常，那么计算就会停止，k依然是1.
        int k=1;
        try {
            int m = forgetIt()/(k = 2) ;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Now k = " + k);
        }

        //如下计算会先计算操作数，再执行除操作。
        try {
            int n = 1 / (0 * forgetIt());
        } catch (Exception e) {
            System.out.println(e);
        }

        //参数列表从左到有求值。所以只有最后一个参数为gone。参数中间有异常，则后面的参数停止求值。
        String s = "going, ";
        print3(s, s, s = "gone");
    }

    static int forgetIt() throws Exception {
        throw new Exception("退出");
    }

    static void print3(String a, String b, String c) {
        System.out.println(a + b + c);
    }
}
