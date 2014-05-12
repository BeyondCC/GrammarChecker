package com.grammarchecker.parser;

public class Token {

	public enum Kind{
		TOKEN_WORD,
		TOKEN_ATTRIBUTE,
		TOKEN_SENTENCE,
		TOKEN_PUNCTION,
		TOKEN_OTHERS,
	};
	// content for each tag
	private String content;
	private Kind kind;
	
	public Token(){
		
	}
	
	public Token(Kind kind, String content){
		this.kind = kind;
		this.content = content;
	}
	
	public String toString() {
		String s = null;
		
		s = kind.toString() + " content:" + (content== null ? "<None>" : content);
		return s.toString();
	}

	public String getContent() {
		return content;
	}

	public Kind getKind() {
		return kind;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

}
