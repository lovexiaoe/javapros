import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//用于自己临时调试一些程序。
public class Test {
	public static void main(String[] args) {
		String json1="[{'orderAmount' : 7900,'discount' : 600,'startWeight' : 20000,'increaseAmount' : 100}]";
		String json2="{	'calculateType':1,'calculateRule':[{	'startOrderAmount':600,	'endOrderAmount':1600,'payment':600,'startWeight' : 20000,'increaseAmount' : 100}]}";

		//parseObj(json1);
		//parseObj(json2);
		testStringbuffer();
	}
	public static void parseObj(String json){
		Object ruleConfig = JSON.parse(json);
		if (JSONObject.class.isInstance(ruleConfig))
			System.out.println("calculateType = "+((JSONObject)ruleConfig).getInteger("calculateType"));
		else{
			System.out.println("calculateType is null");
		}
	}
	static void  testStringbuffer(){
		StringBuilder sb=new StringBuilder("1");
		System.out.println(sb.toString());
		sb.deleteCharAt(sb.length()-1);
		System.out.println(sb.toString());
		System.out.println(sb.length());
	}
}
