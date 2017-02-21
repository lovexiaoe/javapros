package com.zhaoyu.io.textinputoutput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TextIOTest {
	public static void main(String[] args) {

		InputStreamReader in;
		try {
			in = new InputStreamReader(new FileInputStream(
					"E:/eclipseworkspace/studyssh/javapros/src/com/zhaoyu/io/textinputoutput/record.txt"), "ISO8859-1");

			Scanner sc = new Scanner(in);
			while (sc.hasNext()) {
				System.out.println(sc.next());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
