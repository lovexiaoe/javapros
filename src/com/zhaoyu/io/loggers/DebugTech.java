package com.zhaoyu.io.loggers;

public class DebugTech {
	public static void main(String[] args) {
		System.out.println("调试技术");
		// 此句用来跟踪堆栈，不管是否有异常抛出。
		Thread.dumpStack();
	}

	// 在启动时，使用-verbose标志启动java虚拟机，这样 就可以看到类加载过程。用于诊断类路径引发的问题。

	/*
	 * java 5 增加了对java应用程序进行监控和管理的支持，提供了一个jconsole的图形工具。
	 * 它允许使用虚拟机中的代理装置跟踪内存消耗、线程使用、类加载等情况。
	 * 在linux/UNIX环境下，运行ps实用工具，
	 * 在window下，使用任务管理器。然后运行jconsole程序。
	 */
}
