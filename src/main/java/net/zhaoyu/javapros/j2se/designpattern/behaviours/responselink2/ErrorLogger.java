package net.zhaoyu.javapros.j2se.designpattern.behaviours.responselink2;

public class ErrorLogger extends AbstractLogger {
	public ErrorLogger(int level) {
		this.level = level;
	}

	@Override
	protected void write(String message) {
		System.out.println("Error console::logger: " + message);
	}

}
