package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OpenAccount {
	
	  public static void openAccount(Scanner scanner) throws NumberFormatException
	  {
	        try (Connection connection = JDBCConnection.getConnection()) 
	        {
	            System.out.print("Enter Account ID: ");
	            int id = Integer.parseInt(scanner.nextLine());

	            System.out.print("Enter Name: ");
	            String name = scanner.nextLine();

	            if (name == null || name.trim().isEmpty())
	            {
	                throw new IllegalArgumentException("Name cannot be empty.");
	            }

	            boolean isNumeric = true;
	            for (char c : name.trim().toCharArray()) 
	            {
	                if (!Character.isDigit(c)) 
	                {
	                    isNumeric = false;
	                    break;
	                }
	            }

	            if (isNumeric) 
	            {
	                throw new IllegalArgumentException("Name cannot be in numbers.");
	            }

	            if (id <= 0)
	            {
	                throw new IllegalArgumentException("ID must be positive.");
	            }

	            PreparedStatement checkStatement = connection.prepareStatement("SELECT id FROM accounts WHERE id = ?");
	            checkStatement.setInt(1, id);
	            ResultSet resultSet = checkStatement.executeQuery();

	            if (resultSet.next()) 
	            {
	                System.out.println("Account ID already exists.");
	                return;
	            }

	            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (id, name, balance) VALUES (?, ?, 0.0)");
	            statement.setInt(1, id);
	            statement.setString(2, name);
	            statement.executeUpdate();

	            System.out.println("Account opened successfully!");

	        }
	        catch (SQLException | IllegalArgumentException e) 
	        {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
}
