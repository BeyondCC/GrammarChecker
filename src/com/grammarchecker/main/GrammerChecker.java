package com.grammarchecker.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import com.grammarchecker.model.Ngram;
import com.grammarchecker.parser.GrammarCheckerParser;
import com.grammarchecker.parser.Token;
import com.grammarchecker.service.NgramService;

public class GrammerChecker {

	/**
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		 //testMap();
		//HEJkfa
		
		//testSingleFile();
		testWholeDir();
	}
	
	public static void testMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2= new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		
		HashMap<String, String> map4 = new HashMap<String, String>();
		HashMap<String, String> map5 = new HashMap<String, String>();
		
		HashMap<String, String> map6 = new HashMap<String, String>();
		HashMap<String, String> map7 = new HashMap<String, String>();
		HashMap<String, String> map9 = new HashMap<String, String>();
		HashMap<String, String> map8 = new HashMap<String, String>();
		HashMap<String, String> map10 = new HashMap<String, String>();
		HashMap<String, String> map11 = new HashMap<String, String>();
		HashMap<String, String> map12 = new HashMap<String, String>();
		HashMap<String, String> map13 = new HashMap<String, String>();
		HashMap<String, String> map14 = new HashMap<String, String>();
		HashMap<String, String> map15 = new HashMap<String, String>();
		HashMap<String, String> map16 = new HashMap<String, String>();
		HashMap<String, String> map17 = new HashMap<String, String>();
		HashMap<String, String> map18 = new HashMap<String, String>();
		HashMap<String, String> map19 = new HashMap<String, String>();
		HashMap<String, String> map20 = new HashMap<String, String>();
		
		for(int i = 0 ; i < 15000000; i++){
			
			map.put(String.valueOf(i), "sdaf");
//			map1.put(String.valueOf(i), "11");
//			map2.put(String.valueOf(i), "11");
//			map3.put(String.valueOf(i), "11");
//			
//			map4.put(String.valueOf(i), "11");
//			map5.put(String.valueOf(i), "11");
//			
//			map6.put(String.valueOf(i), "11");
//			map7.put(String.valueOf(i), "11");
//			
//			map8.put(String.valueOf(i), "11");
//			map9.put(String.valueOf(i), "11");
//			map10.put(String.valueOf(i), "11");
//			
//			map11.put(String.valueOf(i), "11");
//			map12.put(String.valueOf(i), "11");
//			map13.put(String.valueOf(i), "11");
//			map14.put(String.valueOf(i), "11");
//			map15.put(String.valueOf(i), "11");
//			map16.put(String.valueOf(i), "11");
//			
//			map17.put(String.valueOf(i), "11");
//			map18.put(String.valueOf(i), "11");
//			map19.put(String.valueOf(i), "11");
//			map20.put(String.valueOf(i), "11");
		}
		
		System.out.println("haha");
	}
	public static void test() {

		Runtime run = Runtime.getRuntime(); 

		long max = run.maxMemory(); 

		long total = run.totalMemory(); 

		long free = run.freeMemory(); 

		long usable = max - total + free; 

		System.out.println("鏈�ぇ鍐呭瓨 = " + max); 
		System.out.println("宸插垎閰嶅唴瀛�= " + total); 
		System.out.println("宸插垎閰嶅唴瀛樹腑鐨勫墿浣欑┖闂�= " + free); 
		System.out.println("鏈�ぇ鍙敤鍐呭瓨 = " + usable); 
		
		
		String s = "to_&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;";
		System.out.println("the length:" + s.length());
	}

	public static void testSingleFile() throws Exception {
		long stime;
		long ltime;
		stime = System.currentTimeMillis();
		InputStream fstream;
		BufferedReader reader;
		// String fname = "/home/nlp/Documents/bnc/bnc/A/A0/A05";
		// DJK F
		//HGBC
	//	String fname = "/home/nlp/Documents/bnc/bnc/A/A0/A05";
		
	//	String fname = "/home/nlp/Documents/bnc/bnc/G/G3/G3P";
		String fname = "/home/nlp/Documents/bnc/bnc/F/FA/FA8";
		//String fname = "/home/nlp/Documents/bnc/bnc/H/HG/HGN";
		// String fname = "/home/nlp/Documents/bnc/bnc/E/ED/ED6";
		GrammarCheckerParser parser;

		try {
			fstream = new BufferedInputStream(new FileInputStream(fname));
			reader = new BufferedReader(new FileReader(fname));
			parser = new GrammarCheckerParser(fname, reader);
			parser.parserSevenGram();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testWholeDir() throws Exception {

		long stime;
		long ltime;
		stime = System.currentTimeMillis();
		InputStream fstream;
		BufferedReader reader;
		// String fname = "/home/nlp/Documents/bnc/bnc/A/A0/A00";
		String fname;
		GrammarCheckerParser parser;

		//String path = "D:\\netcode\\bnc";
		String path = "/home/nlp/Documents/bnc/bnc";
		File file = new File(path);
		/*
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("the5'-3'-3", "sdfa");

		if (map.get("the5'-3'-3".toLowerCase()) != null)
			System.out.println("haha");
		else
			System.out.println("cry");
        */
		// NgramService service = new NgramService();
		//
		// Ngram result = service.findNgram("the_reality");
		//
		// if (result != null) {
		// System.out.println("the result: " + result.getWordGroup()
		// + result.getWordAttribute() + result.getFrequence());
		// }

		int count = 0;
		if (file.isDirectory()) {
			File[] subDir = file.listFiles();

			for (int i = 0; i < subDir.length; i++) {
				if (subDir[i].isDirectory()) {
					System.out.println("this is dir:" + subDir[i].getName());

					File ssubArr[] = subDir[i].listFiles();

					for (int j = 0; j < ssubArr.length; j++) {
						if (ssubArr[j].isDirectory()) {
							System.out.println("this is dir: "
									+ ssubArr[j].getName());

							File fileArr[] = ssubArr[j].listFiles();

							for (int k = 0; k < fileArr.length; k++) {
								if (fileArr[k].isFile()) {
									System.out.println("this is file: "
											+ ssubArr[j].toString() + "/"
											+ fileArr[k].getName());
									fname = ssubArr[j].toString() + "/"
											+ fileArr[k].getName();

									try {
										fstream = new BufferedInputStream(
												new FileInputStream(fname));
										reader = new BufferedReader(
												new FileReader(fname));
										parser = new GrammarCheckerParser(
												fname, reader);
										parser.parserSevenGram();
										fstream.close();
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}

				}
			}

		}
		System.out.println(count);
	}

}
