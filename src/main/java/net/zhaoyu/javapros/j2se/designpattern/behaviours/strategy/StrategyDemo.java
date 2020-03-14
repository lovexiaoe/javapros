package net.zhaoyu.javapros.j2se.designpattern.behaviours.strategy;

/**
 * 策略模式针对不同的情况，环境需要执行同一个动作的算法不一样，重点在于同一个动作，不同算法这两点。
 * 比如说，不同等级的会员购买一类商品的折扣不一样，那么购买就是同一个动作，算法不同则体现在折扣。
 *
 * 如spring中的Resource接口就是一种对资源访问策略的一种抽象，提供如下的实现类：UrlResource，FileSystemResource
 * ClassPathResource。并定义了如下等方法对资源进行访问，getInputStream(),isOpen(),getDescription()。
 */
public class StrategyDemo {
	public static void main(String[] args) {
		Context context = new Context(new OperationAdd());
		System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

		context = new Context(new OperationSubstract());
		System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

		context = new Context(new OperationMultiply());
		System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
	}
}
