package net.zhaoyu.javapros.j2se.designpattern.behaviours.command;

public class CommandDemo {
	public static void main(String[] args) {
		Stock abcStock = new Stock();
		BuyStock bs = new BuyStock(abcStock);
		SellStock ss = new SellStock(abcStock);

		Broker broker = new Broker();
		broker.takeOrder(bs);
		broker.takeOrder(ss);

		broker.placeOrders();
	}
}
