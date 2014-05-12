package com.grammarchecker.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;

import com.grammarchecker.model.Ngram;
import com.grammarchecker.service.NgramService;
import com.grammarchecker.utils.JDBC;

public class GrammarCheckerParser {

	private String fname;
	private InputStream fstream;
	private BufferedReader reader;
	private GrammarCheckerLexer lexer;
	private Token current;
	private LinkedList<Token> list = new LinkedList<Token>();
	private LinkedList<Ngram> listAfterHandle = new LinkedList<Ngram>();
	private static HashMap<String, String> hashNgram = new HashMap<String, String>();
	private NgramService service = new NgramService();

	public GrammarCheckerParser(String fname, InputStream fstream)
			throws IOException {
		this.fname = fname;
		this.fstream = fstream;

		lexer = new GrammarCheckerLexer(fname, fstream);
		current = lexer.nextToken();
	}

	public GrammarCheckerParser(String fname, BufferedReader reader)
			throws IOException {
		this.fname = fname;
		this.reader = reader;

		lexer = new GrammarCheckerLexer(fname, reader);
		list = lexer.nextTokenList();
	}

	public void parser() throws Exception {
		Connection conn = JDBC.createConnection();
		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;

		// for (int i = 0; i < 10; i++)
		// list = lexer.nextTokenList();

		while (list != null) {
			
			System.out.println("sss" + list.size());

			preWordWithAttr = null;
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);

				if (current.getKind() == Token.Kind.TOKEN_WORD) {
					if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
						if (preWordWithAttr != null) {
							String attrWithWord[] = preWordWithAttr.split("_");

							ngram = new Ngram();
							ngram.setWordAttribute(attrWithWord[0] + "_"
									+ pre.getContent());
							ngram.setWordGroup(attrWithWord[1] + "_"
									+ current.getContent());
							count++;

							service.addNgrambyup(conn, ngram);
							// service.deleteNgram(conn, ngram);
							// if(hashNgram.get(ngram.getWordGroup().toLowerCase())
							// == null){
							// hashNgram.put(ngram.getWordGroup().toLowerCase(),
							// ngram.getWordAttribute());
							// listAfterHandle.add(ngram);
							// }

							// listAfterHandle.add(ngram);
							preWordWithAttr = pre.getContent() + "_"
									+ current.getContent();
							// }

						} else {
							preWordWithAttr = pre.getContent() + "_"
									+ current.getContent();

						}
					} else if (current.getKind() == Token.Kind.TOKEN_OTHERS
							|| current.getKind() == Token.Kind.TOKEN_PUNCTION) {
						preWordWithAttr = null;
					}
				}

				pre = current;

			}
			list = null;
			list = lexer.nextTokenList();
		}

		// service.batchAddNgram(listAfterHandle);
		JDBC.close(conn);
		System.out.println("add is done");
		// service.deleteNgram();
	}

	public void parserThreeGram() throws Exception {
		Connection conn = JDBC.createConnection();
		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;
		

		while (list != null) {

			preWordWithAttr = null;
			count = 0;
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);

				if (current.getKind() == Token.Kind.TOKEN_WORD) {
					if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
						if (preWordWithAttr != null) {
						
							if(count== 2){
								
								String attrWithWord[] = preWordWithAttr.split("_");
								ngram = new Ngram();
								ngram.setWordAttribute(attrWithWord[0] + "_" +
										attrWithWord[2] + "_"
										+ pre.getContent());
								ngram.setWordGroup(attrWithWord[1] + "_" +
										attrWithWord[3] + "_"
										+ current.getContent());

								service.addNgrambyup(conn, ngram);
								preWordWithAttr = attrWithWord[2] + "_" + attrWithWord[3] +
										"_" +pre.getContent() + "_" + current.getContent();
							}else if(count == 1){
								count++;
								preWordWithAttr = preWordWithAttr + "_" + pre.getContent() + "_" + current.getContent();
							}
							

						} else {
							preWordWithAttr = pre.getContent() + "_"
									+ current.getContent();
							count++;
						}
					} else if (current.getKind() == Token.Kind.TOKEN_OTHERS
							|| current.getKind() == Token.Kind.TOKEN_PUNCTION) {
						preWordWithAttr = null;
						count = 0;
					}
				}

				pre = current;

			}
			list = null;
			list = lexer.nextTokenList();
		}

		// service.batchAddNgram(listAfterHandle);
		JDBC.close(conn);
		System.out.println("add is done");
		// service.deleteNgram();
	}

	public void parserFourGram() throws Exception {
		Connection conn = JDBC.createConnection();
		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;
		

		while (list != null) {

			preWordWithAttr = null;
			count = 0;
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);

				if (current.getKind() == Token.Kind.TOKEN_WORD) {
					if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
						if (preWordWithAttr != null) {
						
							if(count== 3){
								
								String attrWithWord[] = preWordWithAttr.split("_");
								

					
								ngram = new Ngram();
								ngram.setWordAttribute(attrWithWord[0] + "_" 
										+ attrWithWord[2] + "_"
										+ attrWithWord[4] + "_"
										+ pre.getContent());
								ngram.setWordGroup(attrWithWord[1] + "_"
										+ attrWithWord[3] + "_"
										+ attrWithWord[5] + "_"
										+ current.getContent());

								service.addNgrambyup(conn, ngram);
								preWordWithAttr = attrWithWord[2] + "_" + attrWithWord[3] +"_"
										+ attrWithWord[4] + "_" + attrWithWord[5] 
										+ "_" +pre.getContent() + "_" + current.getContent();
							}else if(count == 1 || count == 2){
								count++;
								preWordWithAttr = preWordWithAttr + "_" + pre.getContent() + "_" + current.getContent();
							}
							

						} else {
							preWordWithAttr = pre.getContent() + "_"
									+ current.getContent();
							count++;
						}
					} else if (current.getKind() == Token.Kind.TOKEN_OTHERS
							|| current.getKind() == Token.Kind.TOKEN_PUNCTION) {
						preWordWithAttr = null;
						count = 0;
					}
				}

				pre = current;

			}
			list = null;
			list = lexer.nextTokenList();
		}

		// service.batchAddNgram(listAfterHandle);
		JDBC.close(conn);
		System.out.println("add is done");
		// service.deleteNgram();
	}
	
	public void parserFiveGram() throws Exception {
		Connection conn = JDBC.createConnection();
		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;
		

		while (list != null) {

			preWordWithAttr = null;
			count = 0;
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);

				if (current.getKind() == Token.Kind.TOKEN_WORD) {
					if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
						if (preWordWithAttr != null) {
						
							if(count== 4){
								
								String attrWithWord[] = preWordWithAttr.split("_");
								ngram = new Ngram();
								ngram.setWordAttribute(attrWithWord[0] + "_" 
										+ attrWithWord[2] + "_"
										+ attrWithWord[4] + "_"
										+ attrWithWord[6] + "_"
										+ pre.getContent());
								ngram.setWordGroup(attrWithWord[1] + "_"
										+ attrWithWord[3] + "_"
										+ attrWithWord[5] + "_"
										+ attrWithWord[7] + "_"
										+ current.getContent());

								service.addNgrambyup(conn, ngram);
								preWordWithAttr = attrWithWord[2] + "_" + attrWithWord[3] +"_"
										+ attrWithWord[4] + "_" + attrWithWord[5] + "_"
										+ attrWithWord[6] + "_" + attrWithWord[7] 
										+ "_" +pre.getContent() + "_" + current.getContent();
							}else if(count == 1 || count == 2 || count == 3){
								count++;
								preWordWithAttr = preWordWithAttr + "_" + pre.getContent() + "_" + current.getContent();
							}
							

						} else {
							preWordWithAttr = pre.getContent() + "_"
									+ current.getContent();
							count++;
						}
					} else if (current.getKind() == Token.Kind.TOKEN_OTHERS
							|| current.getKind() == Token.Kind.TOKEN_PUNCTION) {
						preWordWithAttr = null;
						count = 0;
					}
				}

				pre = current;

			}
			list = null;
			list = lexer.nextTokenList();
		}

		// service.batchAddNgram(listAfterHandle);
		JDBC.close(conn);
		System.out.println("add is done");
		// service.deleteNgram();
	}
	
	public void parserSixGram() throws Exception {
		Connection conn = JDBC.createConnection();
		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;
		

		while (list != null) {

			preWordWithAttr = null;
			count = 0;
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);

				if (current.getKind() == Token.Kind.TOKEN_WORD) {
					if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
						if (preWordWithAttr != null) {
						
							if(count== 5){
								
								String attrWithWord[] = preWordWithAttr.split("_");
								ngram = new Ngram();
								ngram.setWordAttribute(attrWithWord[0] + "_" 
										+ attrWithWord[2] + "_"
										+ attrWithWord[4] + "_"
										+ attrWithWord[6] + "_"
										+ attrWithWord[8] + "_"
										+ pre.getContent());
								ngram.setWordGroup(attrWithWord[1] + "_"
										+ attrWithWord[3] + "_"
										+ attrWithWord[5] + "_"
										+ attrWithWord[7] + "_"
										+ attrWithWord[9] + "_"
										+ current.getContent());

								service.addNgrambyup(conn, ngram);
								preWordWithAttr = attrWithWord[2] + "_" + attrWithWord[3] +"_"
										+ attrWithWord[4] + "_" + attrWithWord[5] + "_"
										+ attrWithWord[6] + "_" + attrWithWord[7] + "_"
										+ attrWithWord[8] + "_" + attrWithWord[9] + "_" 
										+pre.getContent() + "_" + current.getContent();
							}else if(count == 1 || count == 2 || count == 3 || count == 4){
								count++;
								preWordWithAttr = preWordWithAttr + "_" + pre.getContent() + "_" + current.getContent();
							}
							

						} else {
							preWordWithAttr = pre.getContent() + "_"
									+ current.getContent();
							count++;
						}
					} else if (current.getKind() == Token.Kind.TOKEN_OTHERS
							|| current.getKind() == Token.Kind.TOKEN_PUNCTION) {
						preWordWithAttr = null;
						count = 0;
					}
				}

				pre = current;

			}
			list = null;
			list = lexer.nextTokenList();
		}

		// service.batchAddNgram(listAfterHandle);
		JDBC.close(conn);
		System.out.println("add is done");
		// service.deleteNgram();
	}
	
	public void parserSevenGram() throws Exception {
		Connection conn = JDBC.createConnection();
		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;
		

		while (list != null) {

			preWordWithAttr = null;
			count = 0;
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);

				if (current.getKind() == Token.Kind.TOKEN_WORD) {
					if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
						if (preWordWithAttr != null) {
						
							if(count== 6){
								
								String attrWithWord[] = preWordWithAttr.split("_");
								ngram = new Ngram();
								ngram.setWordAttribute(attrWithWord[0] + "_" 
										+ attrWithWord[2] + "_"
										+ attrWithWord[4] + "_"
										+ attrWithWord[6] + "_"
										+ attrWithWord[8] + "_"
										+ attrWithWord[10] + "_"
										+ pre.getContent());
								ngram.setWordGroup(attrWithWord[1] + "_"
										+ attrWithWord[3] + "_"
										+ attrWithWord[5] + "_"
										+ attrWithWord[7] + "_"
										+ attrWithWord[9] + "_"
										+ attrWithWord[11] + "_"
										+ current.getContent());

								service.addNgrambyup(conn, ngram);
								preWordWithAttr = attrWithWord[2] + "_" + attrWithWord[3] +"_"
										+ attrWithWord[4] + "_" + attrWithWord[5] + "_"
										+ attrWithWord[6] + "_" + attrWithWord[7] + "_"
										+ attrWithWord[8] + "_" + attrWithWord[9] + "_" 
										+ attrWithWord[10] + "_" + attrWithWord[11] + "_" 
										+pre.getContent() + "_" + current.getContent();
							}else if(count == 1 || count == 2 || count == 3 || count == 4 || count == 5){
								count++;
								preWordWithAttr = preWordWithAttr + "_" + pre.getContent() + "_" + current.getContent();
							}
							

						} else {
							preWordWithAttr = pre.getContent() + "_"
									+ current.getContent();
							count++;
						}
					} else if (current.getKind() == Token.Kind.TOKEN_OTHERS
							|| current.getKind() == Token.Kind.TOKEN_PUNCTION) {
						preWordWithAttr = null;
						count = 0;
					}
				}

				pre = current;

			}
			list = null;
			list = lexer.nextTokenList();
		}

		// service.batchAddNgram(listAfterHandle);
		JDBC.close(conn);
		System.out.println("add is done");
		// service.deleteNgram();
	}
	
	public void parserWithTokenByChar() throws Exception {
		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;

		for (int i = 0; i < 10; i++)
			list = lexer.nextTokenList();

		while (current != null) {
			if (current.getKind() == Token.Kind.TOKEN_WORD) {
				if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
					if (preWordWithAttr != null) {
						String attrWithWord[] = preWordWithAttr.split("_");

						ngram = new Ngram();
						ngram.setWordAttribute(attrWithWord[0] + "_"
								+ pre.getContent());
						ngram.setWordGroup(attrWithWord[1] + "_"
								+ current.getContent());

						count++;
						service.addNgram(ngram);
						// if (count == 200)
						// break;
						preWordWithAttr = pre.getContent() + "_"
								+ current.getContent();
					} else {
						preWordWithAttr = pre.getContent() + "_"
								+ current.getContent();

					}
				} else if (current.getKind() == Token.Kind.TOKEN_OTHERS
						|| current.getKind() == Token.Kind.TOKEN_PUNCTION) {
					preWordWithAttr = null;
				}
			}

			pre = current;
			current = lexer.nextToken();
		}
	}

	public void writeToFile() throws IOException {

		String path = "C:\\Users\\lenovo\\Desktop\\1.txt";
		File f = new File(path);

		if (f.exists()) {
			f.delete();
			f = new File(path);
		}

		if (f.createNewFile()) {
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			while (current != null) {
				System.out.println(current.toString());
				output.write(current.toString());
				output.write('\n');
				current = lexer.nextToken();
			}
			output.close();
		} else
			System.out.println("fail to create the file");

	}
}
