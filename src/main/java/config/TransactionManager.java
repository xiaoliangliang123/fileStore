package config;

import java.sql.Connection;


public class TransactionManager {

	ThreadLocal<Connection> threadLocalConnection = null;
	
	public TransactionManager(ThreadLocal<Connection> threadLocalConnection) {
		this.threadLocalConnection = threadLocalConnection;
	}

	public boolean isBeginThreadLocalConnection() {
		
		return threadLocalConnection.get() != null;
	}

	public void setThreadLocalConnection(Connection conn) {
		
		threadLocalConnection.set(conn);
	}

	public void removeThreadLocalConnection() {
		
		Connection conn =  threadLocalConnection.get();
		threadLocalConnection.remove();
		DBTransaction.close(conn);
	}

	public static boolean valiateIsBeginThreadConnection(
			ThreadLocal<Connection> threadConnection) {
		
		return threadConnection.get() != null;
	}

	
}
