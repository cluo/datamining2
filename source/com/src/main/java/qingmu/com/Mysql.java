package qingmu.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 数据库辅助工具
 * 桑槿
 * 青木数字
 * 2016/2
 * */
public class Mysql {
	Connection conn = null;
	Statement stmt = null;
	public Mysql() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/qingmu?"
				+ "user=root&password=6833066&useUnicode=true&characterEncoding=UTF8";
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection(url);
		this.stmt = conn.createStatement();

	}
	public Mysql(String url) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection(url);
		this.stmt = conn.createStatement();

	}
	public void closeall() {
		try {
			this.stmt.close();
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		ResultSet rs = this.stmt.executeQuery(sql);
		return rs;

	}
	
	public boolean execute(String sql) throws SQLException {
		return this.stmt.execute(sql);

	}

	/**
	 * 将包含非法字符的字符串转换成可以被解析的原生SQL字符串
	 * @example 去掉ok?
	 * @return
	 */
	public static String convertForbiddenStrToValidSQLString(String forbiddenString){
	    String forbiddenChars="@※￥<>\\+#*&^$%\'\"|";
	    StringBuffer retStringBuffer = new StringBuffer("");
	     
	    char[] chars = forbiddenString.toCharArray();
	    for (char c : chars) {
	        boolean isSpecialChar = forbiddenChars.contains(String.valueOf(c));
	       //不包含特殊字符
	        if(!isSpecialChar){
	            retStringBuffer.append(c);;
	        //包含特殊子字符
	        }else {
	        }
	    }    
	     
	    return retStringBuffer.toString();
	}
	
	public static void main(String[] argu) {
		
		String s=convertForbiddenStrToValidSQLString("''''hjhh''''''''''''''''''''");
		System.out.println(s);
	}
		/*try {
			Mysql ss = new Mysql();
			String sql = "SELECT distinct title FROM qingmu.tmp_order_detail_for_skx where merchant_sn like '13514C%' order by import_date desc limit 1;";
			ResultSet s = ss.executeQuery(sql);
			while (s.next()) {
				System.out.println(s.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
