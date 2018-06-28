package config;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 15:51
 * @Description:
 */
public class Transaction {

	TransactionManager tm = null;
	
	public Transaction(TransactionManager tm) {
		
		this.tm = tm ;
		
	}

    public void begin() throws ClassNotFoundException, SQLException {
		
		boolean isBegin =  tm.isBeginThreadLocalConnection();
	    if(!isBegin){
		    Connection conn = DBTransaction.getQueryModelConnection();
		    tm.setThreadLocalConnection(conn);
		    
	    }
	    
	}

	public void end() {
		
		boolean isBegin =  tm.isBeginThreadLocalConnection();
	    if(isBegin){
		    tm.removeThreadLocalConnection();
		    
	    }
		
	}

}
