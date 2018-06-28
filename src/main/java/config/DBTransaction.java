package config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 15:51
 * @Description:
 */
public class DBTransaction {

	private static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
    

	public static Connection getQueryModelConnection() throws ClassNotFoundException, SQLException {
		
		if(TransactionManager.valiateIsBeginThreadConnection(threadConnection))
			return threadConnection.get();
		Class.forName(Config.queryModelConfig.driverName);
		Connection conn = (Connection) 
		DriverManager.getConnection(Config.queryModelConfig.connectURL,
				Config.queryModelConfig.username,Config.queryModelConfig.password);
		return conn;
	}

	public static void close(Object	 ...args) {
		for(Object ts :args) {
		    try {
		    	if(ts instanceof Connection && ts != null){
		    		if(!TransactionManager.valiateIsBeginThreadConnection(threadConnection))
		    		   ((Connection)ts).close();
		    	}
				else if(ts instanceof Statement  && ts != null){
					((Statement)ts).close();
				}
				else if(ts instanceof PreparedStatement  && ts != null){
					((PreparedStatement)ts).close();
				}
				else if(ts instanceof ResultSet && ts != null){
					((ResultSet)ts).close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Transaction getQueryModelThreadTransaction() {
		
		
		return new Transaction(new TransactionManager(threadConnection));
	}
}
