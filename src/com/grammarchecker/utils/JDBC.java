package com.grammarchecker.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
	private static String url = "jdbc:mysql://localhost:3306/mydatabase";
	private static String user = "root";
	private static String password = "123";
	
	public static Connection createConnection(){
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				System.out.println("�����ʧ�ܣ�");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC����ʧ�ܣ�");
			e.printStackTrace();
		}
		return connection;
	}
	
	public static PreparedStatement getSatement(Connection conn, String sql){
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statement;
	}
	
	
	public static void close(ResultSet resultSet){
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement statement){
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
