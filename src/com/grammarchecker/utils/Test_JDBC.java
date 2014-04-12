package com.grammarchecker.utils;

import java.sql.Connection;

import com.grammarchecker.dao.NgramDao;
import com.grammarchecker.daoimpl.NgramImpl;
import com.grammarchecker.service.NgramService;

public class Test_JDBC {
	public static void main(String[] args) throws Exception
	{
		NgramDao dao = new NgramImpl();
		dao.createNtable();
		
		long stime = System.currentTimeMillis();
//		for(int i=0;i<3000;i++)
//		{
//			Connection conn = JDBC.createConnection();
//			JDBC.close(conn);
//	
//		}
		long ltime = System.currentTimeMillis();
		System.out.println(ltime - stime);
		
	}
}
