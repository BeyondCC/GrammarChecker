package com.grammarchecker.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import com.grammarchecker.model.Ngram;
import com.grammarchecker.service.NgramService;

public class GrammarCheckerParser {

	private String fname;
	private InputStream fstream;
	private BufferedReader reader;
	private GrammarCheckerLexer lexer;
	private Token current;
	private LinkedList<Token> list = new LinkedList<Token>();
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

		Ngram ngram;
		int count = 0;
		Token pre = new Token(null, null);
		String preWordWithAttr = null;

		// for (int i = 0; i < 10; i++)
		// list = lexer.nextTokenList();

		while (list != null) {

			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);

				if (current.getKind() == Token.Kind.TOKEN_WORD) {
					if (pre.getKind() == Token.Kind.TOKEN_ATTRIBUTE) {
						if (preWordWithAttr != null) {
							String attrWithWord[] = preWordWithAttr.split("_");

							if (current.getContent().contains("\'")) {
								preWordWithAttr = attrWithWord[0]
										+ pre.getContent() + "_"
										+ attrWithWord[1]
										+ current.getContent();
							} else {
								ngram = new Ngram();
								ngram.setWordAttribute(attrWithWord[0] + "_"
										+ pre.getContent());
								ngram.setWordGroup(attrWithWord[1] + "_"
										+ current.getContent());
								count++;
								service.addNgram(ngram);
								
								preWordWithAttr = pre.getContent() + "_"
										+ current.getContent();
							}

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
			list = lexer.nextTokenList();
		}

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
