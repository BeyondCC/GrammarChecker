package com.grammarchecker.service;

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
	
	public Ngram findNgram(String wordGroup){
		Ngram ngram = dao.findNgram(wordGroup);
		return ngram;
	}
	
	public void updateNgram(Ngram ngram){
		dao.updategram(ngram);
	}
}
