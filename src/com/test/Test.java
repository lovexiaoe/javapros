import java.util.ArrayList;
import java.util.List;

//用于自己临时调试一些程序。
public class Test {
	public static void main(String[] args) {
		//TestSwap();
		System.out.println("TestSwitch:"+TestSwitch(2));
	}

	/**
	 * java中的参数都是值传递，所以不能改变参数，但是可以改变应用参数的属性值，如数组中的元素。
	 */
	public static void TestSwap(){
		int a=1,b=2;
		System.out.println("int交换前，a="+a+",b="+b);
		swapInt(a,b);
		System.out.println("int交换后，a="+a+",b="+b);
		Integer c=3,d=4;
		System.out.println("Integer交换前，c="+c+",d="+d);
		swapInteger(c,d);
		System.out.println("Integer交换后，c="+c+",d="+d);
	}

	public  static void swapInt(int a,int b){
		int c=b;
		b=a;
		a=c;
	}
	public  static void swapInteger(Integer a,Integer b){
		Integer c=b;
		b=a;
		a=c;
	}

	/**
	 * 当case没有break语句时，会执行匹配成功后的所有语句。,如下当输入2时会执行case2,case3，default
	 * @param i
	 * @return
	 */
	public static int TestSwitch(int i){
		int result=0;
		switch(i){
			case 1: result=result+i;
			case 2: result=result+i*2;
			case 3:result=result+i*3;
			default:result=result+1;
		}
		return result;
	}
}