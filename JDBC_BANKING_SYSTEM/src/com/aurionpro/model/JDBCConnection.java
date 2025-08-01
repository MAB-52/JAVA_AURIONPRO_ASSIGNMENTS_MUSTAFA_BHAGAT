package com.aurionpro.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	//Declare Url, Username, Password...
    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "root";
    private static final String PASSWORD = "Mustafa52!";
    
    public static Connection getConnection() throws SQLException
    {
    	try
    	{
    		//Load and register JDBC Driver...
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	}
    	catch(ClassNotFoundException e)
    	{
    		System.out.println("MySQL Driver Class not found !!!!");
    	}
    	
    	return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
