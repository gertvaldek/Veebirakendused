package dao ;
import java.sql.* ;
import java.util.*;

import org.apache.log4j.Logger;



public class dbconnection {
	static Logger logger = Logger.getLogger(dbconnection.class);

	public dbconnection(){}

	public static Connection getConnection() {
		Connection db_connection=null  ;	
		String pwd = "";
		String usr = "";
		String url = "";

		try{            
			ResourceBundle bundle = ResourceBundle.getBundle("DBConnection");
			Class.forName(bundle.getString("Driver"));
			url = bundle.getString("url");
			usr = bundle.getString("usr");
			pwd = bundle.getString("pwd");
			db_connection = DriverManager.getConnection(url, usr, pwd);
		}catch(Exception ex){  
			logger.error("dbconnection.getConnection():" + ex.getMessage());}
		return db_connection;
	}


	public static void close(final Connection conn) {
		if (conn != null){
			try{
				conn.close();
			}catch (SQLException ex){
				logger.error("dbconnection.close()" +  ex.getMessage());
			}
		}
	}

	public static void closeStatement(final Statement stmt){
		if (stmt != null){
			try{
				stmt.close();
			}catch (SQLException ex){
				logger.error("dbconnection.closeStatement()" + ex.getMessage());
			}
		}
	}


	public  static void closeResultSet(final ResultSet rs){
		if (rs != null){
			try{
				rs.close();
			}catch (SQLException ex){
				logger.error("dbconnection.closeResult()" + ex.getMessage());
			}
		}
	}

	public static ResultSet executeQuery(String sql){
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			return result;
		} catch (SQLException e) {
			return null;
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Yhenduse sulgemine eba6nnestus!");
			}
		}  
	}    

}

