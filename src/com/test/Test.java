import java.util.ArrayList;
import java.util.List;

//用于自己临时调试一些程序。
public class Test {
	public static void main(String[] args) {
		List<Integer> ints=new ArrayList<Integer>();
		int i=0;
		while(i++<3){
			ints.add(new Integer(2));
		}
		ints.remove(new Integer(2));
		System.out.println(ints.size());
		System.out.println(0.1*3==0.3);
		System.out.println((0.1+0.1)==0.2);
		System.out.println((0.3)==0.3);
		System.out.println("asdf"=="asdf");
	}
}