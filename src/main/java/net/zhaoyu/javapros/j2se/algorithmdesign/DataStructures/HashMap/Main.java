package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.HashMap;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    int choice, key;

    HashMap h = new HashMap(7);
    Scanner in = new Scanner(System.in);

    while (true) {
      System.out.println("Enter your Choice :");
      System.out.println("1. Add Key");
      System.out.println("2. Delete Key");
      System.out.println("3. Print Table");
      System.out.println("4. Exit");

      choice = in.nextInt();

      switch (choice) {
        case 1:
          {
            System.out.println("Enter the Key: ");
            key = in.nextInt();
            h.insert(key);
            break;
          }
        case 2:
          {
            System.out.println("Enter the Key delete:  ");
            key = in.nextInt();
            h.delete(key);
            break;
          }
        case 3:
          {
            System.out.println("Print table");
            h.display();
            break;
          }
        case 4:
          {
            in.close();
            return;
          }
        default:
          throw new IllegalStateException("Unexpected value: " + choice);
      }
    }
  }
}
