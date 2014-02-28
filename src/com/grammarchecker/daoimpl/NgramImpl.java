package com.grammarchecker.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.grammarchecker.dao.NgramDao;
import com.grammarchecker.model.Ngram;
import com.grammarchecker.utils.JDBC;

public class NgramImpl implements NgramDao {

	@Override
	public void addNgram(Ngram ngram) throws Exception {
		Connection conn = JDBC.createConnection();

		String wordAfterHandle = ngram.getWordGroup();

		if (ngram.getWordGroup().contains("'")) {
			//System.out.println(ngram.getWordGroup());
			String spiltArray[] = ngram.getWordGroup().split("\'");

			wordAfterHandle = spiltArray[0];
			for (int i = 1; i < spiltArray.length; i++)
				wordAfterHandle += "\\" + "\'" + spiltArray[1];
		}
		
		if (findNgram(wordAfterHandle) == null) {
			String sql = "insert into ngram(wordGroup,wordAttribute) values('"
					+ wordAfterHandle + "','" + ngram.getWordAttribute()
					+ "');";

			//System.out.println(sql);
			PreparedStatement stat = JDBC.getSatement(conn, sql);
			stat.executeUpdate();
			JDBC.close(stat);
		}

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


}
