package qingmu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 评论情感打分
 * 桑槿
 * 青木数字
 * 2016/2/25
 * */
public class CommentMark {

	public static Map<String, Integer> drawdictionary(String keyword,
			boolean outsql) throws ClassNotFoundException, SQLException {
		Mysql db = new Mysql();
		Map<String, Integer> map = new HashMap<String, Integer>();
		String sql = "select `content`,`score` from c_dictionary where `type`='"
				+ keyword + "'";
		if (outsql)
			System.out.println(sql);
		ResultSet set = db.executeQuery(sql);
		String content = null;
		int score = 0;
		while (set.next()) {
			content = set.getString("content");
			score = set.getInt("score");
			map.put(content, score);
		}
		return map;
	}

	public static List<String> drawcomment(int rownum, boolean outsql)
			throws ClassNotFoundException, SQLException {
		Mysql db = new Mysql();
		List<String> set = new ArrayList<String>();
		String sql = "select `content` from c_comment limit 0," + rownum;
		if (outsql)
			System.out.println(sql);
		ResultSet rset = db.executeQuery(sql);
		String content = null;
		int score = 0;
		while (rset.next()) {
			content = rset.getString("content");
			set.add(content);
		}
		return set;
	}

	/*
	 * 维度循环 维度 和原始评论条数
	 */
	public static int[] looptype(String type, int rownum, boolean outmatch,
			boolean outnotmatch, boolean outsql) throws ClassNotFoundException,
			SQLException {
		// 维度
		// String type="质量";
		Map<String, Integer> dictionary = drawdictionary(type, outsql);
		Set<String> set = dictionary.keySet();
		int matnum = 0;
		int matscore = 0;
		// 评论条数
		// int rownum=100000;
		List<String> comments = drawcomment(rownum, outsql);
		for (String comment : comments) {
			// System.out.println("开始匹配评论:"+comment);
			for (String content : set) {
				if (comment.contains(content)) {
					if (outmatch) {
						System.out.println("匹配到评论:" + comment);
						System.out.println("字典和分数：" + content + ":"
								+ dictionary.get(content));
					}
					matnum += 1;
					matscore += dictionary.get(content);
					break;
				}

			}
			if (outnotmatch) {
				System.out.println("----------匹配不到-------");
				System.out.println("评论:" + comment);
				System.out.println("----------匹配不到-------");
			}
		}

		System.out.println("匹配到次数" + matnum);
		System.out.println(type + "情感算术平均分" + (float) matscore / matnum);
		int[] detail = { matscore, matnum };
		return detail;
	}

	public static void main(String[] arg) throws ClassNotFoundException,
			SQLException {
		long startTime = System.currentTimeMillis();    //获取开始时间
		
		//维度
		ArrayList<String> type = new ArrayList<String>();
		type.add("质量");
		type.add("舒适度");
		type.add("物流");
		type.add("款式");
		type.add("价格");
		type.add("服务");
		type.add("整体感受");
		
		//维度个数
		int typenum=type.size();
		
		//存储各维度匹配总分和次数
		int[] scoretotal=new int[typenum];
		int[] scorenum=new int[typenum];
		
		//抽取原始评论记录数
		int rownum = 10000;
		
		//是否输出sql
		boolean outsql = false;
		
		//是否输出匹配到的信息
		boolean outmatch = false;
		
		//是否输出匹配不到的信息
		boolean outnotmatch = false;
		
		//开始匹配
		for (int i = 0; i < type.size(); i++) {
			int[] result=looptype(type.get(i), rownum, outmatch, outnotmatch, outsql);
			scorenum[i]=result[1];
			scoretotal[i]=result[0];
		}
		
		for(int i=0;i<typenum;i++){
			System.out.print((float)scoretotal[i]/scorenum[i]+",");
		}
		long endTime = System.currentTimeMillis();    //获取结束时间
		System.out.println("程序运行时间：" + (endTime - startTime)/1000 + "s");    //输出程序运行时间
	}
}
