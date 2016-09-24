package qingmu.com;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 插入字典 桑槿 2016/2/24
 */
public class InsertDictionary {
	public static void insertd(int col1, int col2, String filename, String type) throws ClassNotFoundException, SQLException {
		Mysql db = null;
		ArrayList<String> comments = ReadFromFile.readFileByLines(filename);
		int datarow = 0;
		for (int i=1;i<comments.size();i++) {
			String comment=comments.get(i);
			datarow += 1;
			String[] cs = comment.split(",");
			if (cs[col1].equals("") || cs[col2].equals("")) {
				continue;
			}
			System.out.println(datarow + "行：" + cs[col1] + "" + cs[col2]);

			db = new Mysql();
			cs[col1] = Mysql.convertForbiddenStrToValidSQLString(cs[col1]);
			int score=Integer.parseInt(cs[col2]);
			String sql = "insert into c_dictionary(`content`,`type`,`score`) value(";
			sql+="'"+cs[col1]+"',";
			sql+="'"+type+"',";
			sql+=""+score+")";
			System.out.println(sql);
			try{
				db.execute(sql);
			}
			catch(Exception x){
				x.printStackTrace();
				continue;
			}finally{
				db.closeall();
			}
			
		}

	}

	public static void main(String[] a) throws ClassNotFoundException,
			SQLException {
		System.out.println("start");
		String filename = "D:/女鞋字典打分.csv";
		ArrayList<String> type=new ArrayList<String>();
		type.add("质量");
		type.add("舒适度");
		type.add("物流");
		type.add("款式");
		type.add("价格");
		type.add("服务");
		type.add("整体感受");
		for(int i=0;i<type.size();i++){
			System.out.println(type.get(i));
			insertd((i+1)*2-2, (i+1)*2-1, filename, type.get(i));
		}
	}
}
