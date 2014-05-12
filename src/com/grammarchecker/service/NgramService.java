package com.grammarchecker.service;

import java.sql.Connection;
import java.util.LinkedList;

import com.grammarchecker.dao.NgramDao;
import com.grammarchecker.daoimpl.NgramImpl;
import com.grammarchecker.model.Ngram;

public class NgramService {
	
	NgramDao dao = new NgramImpl();

	public NgramService() {
	}
	
	public void addNgram(Ngram ngram) throws Exception{
		dao.addNgram(ngram);
	}
	
	public void addNgrambyup(Connection conn,Ngram ngram) throws Exception{
		dao.addNgrambyup(conn,ngram);
	}
	
	public void batchAddNgram(LinkedList<Ngram> list) throws Exception{
		dao.batchAddNgram(list);
	}
	
	public Ngram findNgram(String wordGroup){
		Ngram ngram = dao.findNgram(wordGroup);
		return ngram;
	}
	
	public void updateNgram(Ngram ngram){
		dao.updategram(ngram);
	}
	
	public void deleteNgram(){
		dao.deleteNgram();
	}
	
	public void deleteNgram(Connection conn, Ngram ngram){
		dao.deleteNgram(conn, ngram);
	}
	
}
