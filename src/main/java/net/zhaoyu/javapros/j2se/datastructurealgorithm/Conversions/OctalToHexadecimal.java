package net.zhaoyu.javapros.j2se.datastructurealgorithm.Conversions;

import java.util.Scanner;

/**
 * 八进制转16进制
 */
public class OctalToHexadecimal {

  //八进制转10进制
  public static int octToDec(String s) {
    int i = 0;
    for (int j = 0; j < s.length(); j++) {
      char num = s.charAt(j);
      num -= '0';
      i *= 8;
      i += num;
    }
    return i;
  }

  /**
   * 十进制转16进制
   */
  public static String decimalToHex(int d) {
    String digits = "0123456789ABCDEF";
    if (d <= 0) return "0";
    String hex = "";
    while (d > 0) {
      int digit = d % 16;
      hex = digits.charAt(digit) + hex;
      d = d / 16;
    }
    return hex;
  }

  public static void main(String args[]) {

    Scanner input = new Scanner(System.in);
    System.out.print("输入8进制数字: ");

    String oct = input.next();

    int decimal = octToDec(oct);

    String hex = decimalToHex(decimal);
    System.out.println("16进制结果: " + hex);
    input.close();
  }
}
