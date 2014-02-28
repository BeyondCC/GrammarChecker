package com.grammarchecker.dao;


import com.grammarchecker.model.Ngram;

public interface NgramDao {
	
	public void addNgram(Ngram ngram) throws Exception;

	public void delNgram(Ngram ngram);

	public Ngram findNgram(String wordGroup);

	public void updategram(Ngram ngram);
}

