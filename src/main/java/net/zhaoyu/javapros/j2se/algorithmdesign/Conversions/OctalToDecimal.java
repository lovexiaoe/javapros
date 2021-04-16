package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.Scanner;

/**
 * 八进制转10进制
 *
 */
public class OctalToDecimal {

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    System.out.print("八进制输入: ");
    String inputOctal = sc.nextLine();
    int result = convertOctalToDecimal(inputOctal);
    if (result != -1) System.out.println("十进制结果 : " + result);
    sc.close();
  }


  public static int convertOctalToDecimal(String inputOctal) {
    try {
      Integer outputDecimal = Integer.parseInt(inputOctal, 8);
      return outputDecimal;
    } catch (NumberFormatException ne) {
      System.out.println("Invalid Input, Expecting octal number 0-7");
      return -1;
    }
  }
}
