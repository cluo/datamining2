package qingmu.com;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 插入原始评论
 * 桑槿
 * 2016/2/24
 */
public class InsertComment {
	public static void main(String[] a) throws ClassNotFoundException, SQLException{
		System.out.println("start");
		String filename="D:/女鞋评论_01.csv";
		Mysql db=null;
		ArrayList<String> comments=ReadFromFile.readFileByLines(filename);
		for (String comment : comments) {
			db=new Mysql();
			comment=Mysql.convertForbiddenStrToValidSQLString(comment);
			String sql="insert into c_comment(`content`) value('"+comment+"')";
			System.out.println(sql);
			db.execute(sql);
			db.closeall();	
		}
		
		
	}
}
