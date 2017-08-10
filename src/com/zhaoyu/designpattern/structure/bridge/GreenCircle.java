package com.zhaoyu.designpattern.structure.bridge;

public class GreenCircle implements DrawAPI {

	@Override
	public void drawCircle(int radius, int x, int y) {
		System.out.println("Drawing Circle[ color:greens,radius: " + ",x:" + x + "," + y + "]");
	}

}
