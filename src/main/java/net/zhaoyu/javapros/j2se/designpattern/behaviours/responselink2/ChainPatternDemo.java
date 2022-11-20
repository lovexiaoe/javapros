package net.zhaoyu.javapros.j2se.designpattern.behaviours.responselink2;

/**
 * 责任链模式，一个对象需要一条链的各个环节都需要处理。各个环节有明确的上下游关系。如下面例子中用nextLogger
 * 确定了上下游关系。那么可以使用责任了模式，
 * 随便这里使用日志不太准确，但是主要用于说明问题。
 *
 * 和过滤器不同的是，过滤器是针对一个群体的对象使用条件筛选，过滤掉部分对象。
 *
 * 过滤器可以结合责任了使用，达到对一个群体被多个过滤器过滤的效果。
 */
public class ChainPatternDemo {
	public static AbstractLogger getChainOfLogger() {
		AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
		AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
		AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

		errorLogger.setNextLogger(fileLogger);
		fileLogger.setNextLogger(consoleLogger);

		return errorLogger;
	}

	public static void main(String[] args) {
		AbstractLogger loggerChain = getChainOfLogger();

		loggerChain.logMessage(AbstractLogger.INFO, "This is an information.");
		System.out.println("==========");
		loggerChain.logMessage(AbstractLogger.DEBUG, "This is an debug level information.");
		System.out.println("==========");
		loggerChain.logMessage(AbstractLogger.ERROR, "This is an error level information.");
	}
}
