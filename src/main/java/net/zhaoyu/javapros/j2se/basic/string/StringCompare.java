package net.zhaoyu.javapros.j2se.basic.string;

public class StringCompare {
	public static void main(String[] args) {
		String s1 = new String("hello"); // s1 is a copy of "hello"
		// String s1 = "hello"; //这种情况下 s1=="hello"
		String s2 = "goodbye";
		String s3 = "Happy Birthday";
		String s4 = "happy birthday";

		// test for equality
		System.out.println("s1 equals \"hello\"= " + s1.equals("hello"));

		// test for equality with ==
		System.out.println("s1 is the same object as \"hello\"= " + (s1 == "hello"));

		// test for equality (ignore case)
		System.out.printf("%s equals %s with case ignored= " + s3.equalsIgnoreCase(s4) + "\n", s3, s4);

		// compareTo 根据字符串的字符序列比较 ，相等返回0，调用字符串小于参数字符串，返回负数，相反，返回正数。
		System.out.printf("\ns1.compareTo( s2 ) is %d", s1.compareTo(s2));
		System.out.printf("\ns2.compareTo( s1 ) is %d", s2.compareTo(s1));
		System.out.printf("\ns1.compareTo( s1 ) is %d", s1.compareTo(s1));

		// regionMatches (case sensitive), s3从0开始，s4从0开始，比较5位。
		if (s3.regionMatches(0, s4, 0, 5))
			System.out.println("First 5 characters of s3 and s4 match");
		else
			System.out.println("First 5 characters of s3 and s4 do not match");

		// test regionMatches (ignore case)
		if (s3.regionMatches(true, 0, s4, 0, 5))
			System.out.println("First 5 characters of s3 and s4 match with case ignored");
		else
			System.out.println("First 5 characters of s3 and s4 do not match");
	}
}
