package com.grammarchecker.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GrammarCheckerLexer {

	private String fname;
	private InputStream fstream;
	private BufferedReader reader;
	private StringBuffer sb;
	private Stack<Integer> stack;
	private LinkedList<Token>  list;

	public GrammarCheckerLexer(String fname, InputStream fstream) {
		this.fname = fname;
		this.fstream = fstream;
		stack = new Stack<Integer>();
		sb = new StringBuffer();
	}

	public GrammarCheckerLexer(String fname, BufferedReader reader) {
		this.fname = fname;
		this.reader = reader;
		stack = new Stack<Integer>();
		sb = new StringBuffer();
	}

	public LinkedList<Token> nextTokenList() throws IOException {

		int index;
		int offset;
		char scanChar;
		Token token = new Token();
		list = null;
		list = new LinkedList<Token>();
		//File filew = new File("D:\\netcode\\A.txt");
		//BufferedWriter bw = new BufferedWriter(new FileWriter(filew));
		
		String buf = reader.readLine();

		while (buf != null) {
			if ((buf.length()>3)&&(buf.substring(0, 4).contains("<s n"))) {
				// to sub the <s n=xxx>
				//System.out.println("ok");
				
				offset = buf.indexOf('>');
				buf = buf.substring(offset + 1);
				
				for (index = 0;index<buf.length();)
				{
					scanChar = buf.charAt(index);
					// EOF
					if (scanChar == -1)
						return null;
					if (scanChar == '<')
					{
						scanChar = buf.charAt(++index);
						switch (scanChar) {
						case 'w':
							index += 2;
							scanChar = buf.charAt(index);
							sb = new StringBuffer();
							while (scanChar != '>') {
								sb.append(scanChar);
								index++;
								scanChar = buf.charAt(index);
							}
							//index++;
							list.add(new Token(Token.Kind.TOKEN_ATTRIBUTE,
									sb.toString()));
							sb.setLength(0);
							while(index<buf.length()-1)
							{
								scanChar = buf.charAt(++index);
								if(scanChar != '<')
								{
									if(!isIgnore(scanChar))
									{
										sb.append(scanChar);
									}
								}
								else
								{
									break;
								}
							}
							if(sb.length() > 0)
							list.add(new Token(Token.Kind.TOKEN_WORD,
									sb.toString()));			
							break;
						case 'c':
							// distinguish <c pun> with tags begin with char c
							scanChar = buf.charAt(++index);
							if (scanChar == ' ') {
								index++;
								scanChar = buf.charAt(index);
								sb = new StringBuffer();
								while (scanChar != '>') {
									sb.append(scanChar);
									index++;
									scanChar = buf.charAt(index);
								}
								list.add(new Token(Token.Kind.TOKEN_PUNCTION,
										sb.toString()));
								
								//return list;
							} else {
								sb = new StringBuffer();
								sb.append('c');
								while (scanChar != '>') {
									sb.append(scanChar);
									index++;
									scanChar = buf.charAt(index);
								}
								list.add(new Token(Token.Kind.TOKEN_OTHERS,
										sb.toString()));
							}
							
							sb.setLength(0);
							while(index<buf.length()-1)
							{
								scanChar = buf.charAt(++index);
								if(scanChar != '<')
								{
									if(!isIgnore(scanChar))
									{
										sb.append(scanChar);
									}
								}
								else
								{
									break;
								}
							}
							list.add(new Token(Token.Kind.TOKEN_WORD,
									sb.toString()));
							break;
						default:

							sb = new StringBuffer();
							while (scanChar != '>') {
								sb.append(scanChar);
								index++;
								scanChar = buf.charAt(index);
							}
							list.add(new Token(Token.Kind.TOKEN_OTHERS,
									sb.toString()));
							sb.setLength(0);
							while(index<buf.length()-1)
							{
								scanChar = buf.charAt(++index);
								if(scanChar != '<')
								{
									if(!isIgnore(scanChar))
									{
										sb.append(scanChar);
									}
								}
								else
								{
									break;
								}
							}
							list.add(new Token(Token.Kind.TOKEN_WORD,
									sb.toString()));
						}
					}
					else
					{
						index++;
					}
				}
				//System.out.println("ok");
				
			//	System.out.println("size:" + list.size());
				return list;
				
				
				
				/*
				for (index = 0; index < buf.length(); ) {
					scanChar = buf.charAt(index);
		
					while (scanChar == '>' || scanChar == ' ' || scanChar == '\n'
					|| scanChar == '\r' || scanChar == '[' || scanChar == '.'
					|| scanChar== '(' || scanChar == '\"'){
						index++;
						if(index < buf.length())
							scanChar = buf.charAt(index);
						else
							return list;
					}
	
					// EOF
					if (scanChar == -1)
						return null;
					
					if (scanChar == '<') {
						index++;
						scanChar = buf.charAt(index);

						switch (scanChar) {
						case 'w':
							index += 2;
							scanChar = buf.charAt(index);
							sb = new StringBuffer();
							while (scanChar != '>') {
								sb.append(scanChar);
								index++;
								scanChar = buf.charAt(index);
							}
							list.add(new Token(Token.Kind.TOKEN_ATTRIBUTE,
									sb.toString()));
							break;
						case 'c':
							// distinguish <c pun> with tags begin with char c
							index++;
							scanChar = buf.charAt(index);
							if (scanChar == ' ') {
								index++;
								scanChar = buf.charAt(index);
								sb = new StringBuffer();
								while (scanChar != '>') {
									sb.append(scanChar);
									index++;
									scanChar = buf.charAt(index);
								}
								list.add(new Token(Token.Kind.TOKEN_PUNCTION,
										sb.toString()));
								//return list;
							} else {
								sb = new StringBuffer();
								sb.append('c');
								while (scanChar != '>') {
									sb.append(scanChar);
									index++;
									scanChar = buf.charAt(index);
								}
								list.add(new Token(Token.Kind.TOKEN_OTHERS,
										sb.toString()));
							}
							break;
						default:

							sb = new StringBuffer();
							while (scanChar != '>') {
								sb.append(scanChar);
								index++;
								scanChar = buf.charAt(index);
							}
							list.add(new Token(Token.Kind.TOKEN_OTHERS,
									sb.toString()));
						}
					} else {
						// parse the word token
						sb = new StringBuffer();
						while (!isIgnore(scanChar)) {
							// deal with token like 0.300 or H.I.V
							if (scanChar == '.') {
								
								index++;
								scanChar = buf.charAt(index);
								if (!isIgnore(scanChar)) {
									
									sb.append('.');
									sb.append(scanChar);
									if(index < buf.length())
										scanChar = buf.charAt(index);
									else{
										list.add(new Token(Token.Kind.TOKEN_WORD,
												sb.toString()));
										return list;
									}
								} 
							} else {
								sb.append(scanChar);
								index++;
								if(index < buf.length())
									scanChar = buf.charAt(index);
								else{
									list.add(new Token(Token.Kind.TOKEN_WORD,
											sb.toString()));
									return list;
								}
							}
						}
						
						if(sb.length()> 0){
						list.add(new Token(Token.Kind.TOKEN_WORD,
								sb.toString()));
						}else{
							return list;
						}
					}

				}
				
				for(int i = 0; i< list.size(); i++)
					System.out.println(list.get(i).getKind().toString() + " " + list.get(i).getContent());
				
				return list;
				*/

			}
			
			buf = reader.readLine();
			
		}
		//bw.close();
		return null;
		//return list;
		
	}

	public Token nextToken() throws IOException {

		int c;
		String content;

		if (stack.isEmpty())
			c = fstream.read();
		else
			c = stack.pop();

		// if the current char is > or blank or ].,then ignore
		while ((char) c == '>' || (char) c == ' ' || (char) c == '\n'
				|| (char) c == '\r' || (char) c == '[' || (char) c == '.'
				|| (char) c == '(' || (char) c == '\"')
			c = fstream.read();

		// EOF
		if (c == -1)
			return null;

		// parse the tag token
		if ((char) c == '<') {
			c = fstream.read();

			switch (c) {
			case 's':
				while ((char) c != '>')
					c = fstream.read();
				return new Token(Token.Kind.TOKEN_SENTENCE, null);
			case 'w':
				c = fstream.read();
				c = fstream.read();
				sb = new StringBuffer();
				while ((char) c != '>') {
					sb.append((char) c);
					c = fstream.read();
				}
				return new Token(Token.Kind.TOKEN_ATTRIBUTE, sb.toString());
			case 'c':
				// distinguish <c pun> with tags begin with char c
				c = fstream.read();
				if ((char) c == ' ') {
					c = fstream.read();
					sb = new StringBuffer();
					while ((char) c != '>') {
						sb.append((char) c);
						c = fstream.read();
					}
					return new Token(Token.Kind.TOKEN_PUNCTION, sb.toString());
				} else {
					sb = new StringBuffer();
					sb.append('c');
					while ((char) c != '>') {
						sb.append((char) c);
						c = fstream.read();
					}
					return new Token(Token.Kind.TOKEN_OTHERS, sb.toString());
				}

			default:

				sb = new StringBuffer();
				while ((char) c != '>') {
					sb.append((char) c);
					c = fstream.read();
				}
				return new Token(Token.Kind.TOKEN_OTHERS, sb.toString());
			}
		} else {
			// parse the word token
			sb = new StringBuffer();
			while (!isIgnore(c)) {
				// deal with token like 0.300 or H.I.V
				if ((char) c == '.') {
					stack.push(c);
					c = fstream.read();
					if (!isIgnore(c)) {
						stack.pop();
						sb.append('.');
						sb.append((char) c);
						c = fstream.read();
					} else {
						stack.pop();
					}
				} else {
					sb.append((char) c);
					c = fstream.read();
				}
				
			}
			

			if ((char) c == '<')
				stack.push(c);
			// if(sb.toString().contains("Changes"))
			// System.out.println("..." + sb.toString() + "  " +
			// sb.toString().length());
			return new Token(Token.Kind.TOKEN_WORD, sb.toString());
			
			
			
		}
	
		
	}

	public Boolean isIgnore(int c) {

		if ((char) c == ' '|| (char) c == '\n')
			return true;
		else
			return false;
	}

}
