package com.zhaoyu.basic.regex;

public class StringMatches {
	// 验证 first name
	public static boolean validateFirstName(String firstName) {
		return firstName.matches("[A-Z][a-zA-Z]*");
	}

	// 验证 last name
	public static boolean validateLastName(String lastName) {
		return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
	}

	// 验证地址 如：123 Some street
	public static boolean validateAddress(String address) {
		return address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
	}

	// validate city 如：Some city
	public static boolean validateCity(String city) {
		return city.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
	} // end method validateCity

	// 验证电话，匹配如：123-456-789
	public static boolean validatePhone(String phone) {
		return phone.matches("[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}");
	} // end method validatePhone
}
