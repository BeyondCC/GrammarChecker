package com.grammarchecker.model;

public class Ngram {

	private String wordGroup;
	private String wordAttribute;
	private double frequence;

	public String getWordGroup() {
		return wordGroup;
	}

	public void setWordGroup(String wordGroup) {
		this.wordGroup = wordGroup;
	}

	public String getWordAttribute() {
		return wordAttribute;
	}

	public void setWordAttribute(String wordAttribute) {
		this.wordAttribute = wordAttribute;
	}

	public double getFrequence() {
		return frequence;
	}

	public void setFrequence(double frequence) {
		this.frequence = frequence;
	}

}
