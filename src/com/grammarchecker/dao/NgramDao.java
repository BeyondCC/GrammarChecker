package com.grammarchecker.dao;

import java.sql.Connection;
import java.util.LinkedList;

import com.grammarchecker.model.Ngram;

public interface NgramDao {

	public void addNgram(Ngram ngram) throws Exception;

	public void addNgrambyup(Connection conn, Ngram ngram) throws Exception;


	public void batchAddNgram(LinkedList<Ngram> list) throws Exception;

	public void delNgram(Ngram ngram);

	public Ngram findNgram(String wordGroup);

	public void updategram(Ngram ngram);

	public void deleteNgram();

	public void deleteNgram(Connection conn, Ngram ngram);

	public void createNtable() throws Exception;;

	public void createNtables() throws Exception;;
}
