package com.aurionpro.test;

import java.sql.SQLException;
import com.aurionpro.model.BankMenuFacade;

public class BankTest {

	public static void main(String[] args) throws SQLException
	{
		BankMenuFacade bankMenuFacade = new BankMenuFacade();
		bankMenuFacade.start();
	}
	
}
