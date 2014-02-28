package com.grammarchecker.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

import com.grammarchecker.model.Ngram;
import com.grammarchecker.parser.GrammarCheckerParser;
import com.grammarchecker.service.NgramService;

public class GrammerChecker {

	/**
	 * @param args
	 * @throws Exception
	 */
	
	public static void main(String[] args) throws Exception {
		long stime;
		long ltime;
		stime = System.currentTimeMillis();  
		InputStream fstream;
		BufferedReader reader;
		String fname = "/home/nlp/Documents/bnc/bnc/A/A0/A00";
		GrammarCheckerParser parser;

		String path = "D:\\netcode\\bnc";
		File file = new File(path);

//		NgramService service = new NgramService();
//
//		Ngram result = service.findNgram("the_reality");
//
//		if (result != null) {
//			System.out.println("the result: " + result.getWordGroup()
//					+ result.getWordAttribute() + result.getFrequence());
//		}

//		if (file.isDirectory()) {
//			File[] subDir = file.listFiles();
//
//			for (int i = 0; i < subDir.length; i++) {
//				if (subDir[i].isDirectory()) {
//					System.out.println("this is dir:" + subDir[i].getName());
//
//					File ssubArr[] = subDir[i].listFiles();
//
//					for (int j = 0; j < ssubArr.length; j++) {
//						if (ssubArr[j].isDirectory()) {
//							System.out.println("this is dir: "
//									+ ssubArr[j].getName());
//
//							File fileArr[] = ssubArr[j].listFiles();
//
//							for (int k = 0; k < fileArr.length; k++) {
//								if (fileArr[k].isFile()) {
//									System.out.println("this is file: "
//											+ fileArr[k].getName());
//								}
//							}
//						}
//					}
//
//				}
//			}
//
//		}
//		System.out.println("haha");
		

		try {
			fstream = new BufferedInputStream(new FileInputStream(fname));
			reader  = new BufferedReader(new FileReader(fname));			
			parser = new GrammarCheckerParser(fname, reader);
			parser.parser();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ltime = System.currentTimeMillis();  
		System.out.println(ltime-stime);
	}

}
