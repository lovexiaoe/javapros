package net.zhaoyu.javapros.j2se.designpattern.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class Meal {
	private List<Item> items = new ArrayList<Item>();

	public void addItem(Item item) {
		items.add(item);
	}

	public float getCost() {
		float cost = 0f;
		for (Item item : items) {
			cost = cost + item.price();
		}
		return cost;
	}

	public void showItems() {
		for (Item item : items) {
			System.out.print("Item : " + item.name());
			System.out.print(", Packing :  " + item.packing().pack());
			System.out.println(", Price :  " + item.price());
		}
	}
}
