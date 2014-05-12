package com.grammarchecker.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.grammarchecker.dao.NgramDao;
import com.grammarchecker.model.Ngram;
import com.grammarchecker.utils.JDBC;

public class NgramImpl implements NgramDao {
	
	public static final String tableName = "ngram7";
	
	public void createNtables() throws Exception{
		Connection conn = JDBC.createConnection();
		String table_name = "";
		for (int i=97;i<123;i++)
		{
			table_name = "n_gram_"+(char)i;
			String sql = " create table "+table_name+"(wordGroup varchar(100) primary key, wordAttribute varchar(100), frequence double(10,8), count int(10));";
			PreparedStatement stat = JDBC.getSatement(conn, sql);
			stat.executeUpdate();
			JDBC.close(stat);
		}
		JDBC.close(conn);
	}
	
	public void createNtable() throws Exception{
		Connection conn = JDBC.createConnection();
		//String table_name = "n_gram";
		String sql = " create table "+tableName+"(wordGroup varchar(250) , wordAttribute varchar(100), frequence double(10,8), count int(10),primary key(wordGroup));";
		PreparedStatement stat = JDBC.getSatement(conn, sql);
		stat.executeUpdate();
		JDBC.close(stat);
		JDBC.close(conn);
	}
	
	public void addNgrambyup(Connection conn,Ngram ngram) throws Exception{
		String wordAfterHandle = "";
		for(int i=0;i<ngram.getWordGroup().length();i++)
		{
			char c = ngram.getWordGroup().charAt(i);
			if(c == '\'')
			{
				wordAfterHandle += "\\\'";
			}
			else
			{
				wordAfterHandle += c;
			}
		}
		String sql = "insert into " + tableName +"(wordGroup,wordAttribute,frequence,count) values('"
				+ wordAfterHandle + "','" + ngram.getWordAttribute() + "','0','1')" +
				"on duplicate key update count=count+1";
		
		//System.out.println("sql is : " + sql);
		
		PreparedStatement stat = JDBC.getSatement(conn, sql);
		stat.executeUpdate();
		JDBC.close(stat);
		
	}
	
	
	public void deleteNgrambyup(Connection conn, Ngram ngram) throws Exception{
		
	}

	@Override
	public void addNgram(Ngram ngram) throws Exception {
		Connection conn = JDBC.createConnection();

		String wordAfterHandle = ngram.getWordGroup();

		if (ngram.getWordGroup().contains("'")) {
			// System.out.println(ngram.getWordGroup());
			String spiltArray[] = ngram.getWordGroup().split("\'");

			wordAfterHandle = spiltArray[0];
			for (int i = 1; i < spiltArray.length; i++)
				wordAfterHandle += "\\" + "\'" + spiltArray[1];
		}

		String sql = "insert ignore into ngram(wordGroup,wordAttribute) values('"
				+ wordAfterHandle + "','" + ngram.getWordAttribute() + "');";

		// System.out.println(sql);
		PreparedStatement stat = JDBC.getSatement(conn, sql);
		stat.executeUpdate();
		JDBC.close(stat);

		JDBC.close(conn);
	}

	@Override
	public void delNgram(Ngram ngram) {

	}

	@Override
	public Ngram findNgram(String wordGroup) {

		Connection conn = JDBC.createConnection();
		Ngram ngram = new Ngram();

		String sql = "select wordAttribute, frequence from ngram where wordGroup='"
				+ wordGroup + "';";
		PreparedStatement stat = JDBC.getSatement(conn, sql);
		try {
			ResultSet rs = stat.executeQuery();
			if (rs.first()) {
				ngram.setWordGroup(wordGroup);
				ngram.setWordAttribute((String) rs.getObject(1));
				if (rs.getObject(2) != null) {
					ngram.setFrequence((Double) rs.getObject(2));
				}
				return ngram;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JDBC.close(stat);
		JDBC.close(conn);

		return null;
	}

	@Override
	public void updategram(Ngram ngram) {

		Connection conn = JDBC.createConnection();

		String sql = "update  ngram set frequence = " + ngram.getFrequence()
				+ " where " + "wordGroup = '" + ngram.getWordGroup() + "';";

		PreparedStatement stat = JDBC.getSatement(conn, sql);
		try {
			stat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JDBC.close(stat);
		JDBC.close(conn);

	}

	@Override
	public void batchAddNgram(LinkedList<Ngram> list) {
		System.out.println("The total ngram is :" + list.size());

		int count = 0;
		Connection conn = JDBC.createConnection();
		Ngram ngram;

		String sql = "insert ignore into ngram(wordGroup,wordAttribute) values";
		for (int index = 0; index < list.size(); index++) {

			ngram = list.get(index);
			String wordAfterHandle = ngram.getWordGroup();
			char tmp;
			StringBuffer sb = new StringBuffer();

			for (int offset = 0; offset < wordAfterHandle.length(); offset++) {
				if ((tmp = wordAfterHandle.charAt(offset)) == '\'') {
					sb.append('\\');
					sb.append('\'');
				} else {
					sb.append(tmp);
				}
			}

			wordAfterHandle = sb.toString();

			if (index == list.size() - 1) {

				// sql = sql.substring(0, sql.length() - 1) + ";";
				// System.out.println("the end sql :" + sql);

				sql = sql + "('" + wordAfterHandle + "','"
						+ ngram.getWordAttribute() + "');";

				PreparedStatement stat = JDBC.getSatement(conn, sql);
				try {
					stat.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("here:" + sql);
					e.printStackTrace();
				}
				JDBC.close(stat);
				JDBC.close(conn);
				return;
			}

			// if (findNgram(wordAfterHandle) == null) {
			if (count < 1000) {
				sql = sql + "('" + wordAfterHandle + "','"
						+ ngram.getWordAttribute() + "'),";
				count++;
			} else if (count == 1000) {
				sql = sql + "('" + wordAfterHandle + "','"
						+ ngram.getWordAttribute() + "');";

				// System.out.println("the sql is " + sql);
				PreparedStatement stat = JDBC.getSatement(conn, sql);
				try {
					stat.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("error:" + sql);
					e.printStackTrace();
				}
				JDBC.close(stat);

				sql = "insert into ngram(wordGroup,wordAttribute) values";
				count = 0;
			}
			// }
		}
		JDBC.close(conn);
	}

	@Override
	public void deleteNgram() {

		Connection conn = JDBC.createConnection();

		String sql = "delete from ngram";

		PreparedStatement stat = JDBC.getSatement(conn, sql);
		try {
			stat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JDBC.close(stat);
		JDBC.close(conn);

	}
	@Override
	public void deleteNgram(Connection conn, Ngram ngram) {
		String wordAfterHandle = "";
		for(int i=0;i<ngram.getWordGroup().length();i++)
		{
			char c = ngram.getWordGroup().charAt(i);
			if(c == '\'')
			{
				wordAfterHandle += "\\\'";
			}
			else
			{
				wordAfterHandle += c;
			}
		}
		
		String sql = "delete from n_gram where wordGroup = '" + wordAfterHandle + "';";
		
		//System.out.println("sql is : " + sql);
		
		/*mysql>INSERT INTO table (a,b,c) VALUES (1,2,3)

        ->ON DUPLICATE KEY UPDATE c=c+1;
        */

		// System.out.println(sql);
		PreparedStatement stat = JDBC.getSatement(conn, sql);
		try {
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBC.close(stat);
	}



}
