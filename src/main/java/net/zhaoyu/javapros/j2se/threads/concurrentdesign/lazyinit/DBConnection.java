package net.zhaoyu.javapros.j2se.threads.concurrentdesign.lazyinit;

/**
 * 该种懒加载在并发情况下，会出现并发问题。
 */
public class DBConnection {

	private static DBConnection connection;
	
	private DBConnection() {
		
	}
	
	public static DBConnection getConnection(){
		if (connection==null) {
			connection=new DBConnection();
		}
		return connection;
	}
}
