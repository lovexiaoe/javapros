package net.zhaoyu.javapros.j2se.threads.concurrentdesign.lazyinit;

/**
 * 并发安全的懒加载。
 */
public class DBConnectionOK {

	private DBConnectionOK() {
		System.out.printf("%s: Connection created.\n",Thread.currentThread().getName());
	}

    private static class LazyDBConnection {
        private static final DBConnectionOK INSTANCE = new DBConnectionOK();
    }
    
    public static DBConnectionOK getConnection() {
        return LazyDBConnection.INSTANCE;
    }	
	
}
