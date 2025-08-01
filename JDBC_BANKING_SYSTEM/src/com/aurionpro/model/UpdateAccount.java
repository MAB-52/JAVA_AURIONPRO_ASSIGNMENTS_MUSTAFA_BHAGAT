package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class UpdateAccount {

    public static void updateAccountName(Scanner scanner) 
    {
        try (Connection connection = JDBCConnection.getConnection()) 
        {

            System.out.print("Enter your Account ID: ");
            int accountId = Integer.parseInt(scanner.nextLine());

            // Check if account exists and is ACTIVE
            PreparedStatement checkStmt = connection.prepareStatement("SELECT name FROM accounts WHERE id = ? AND status = 'active'");
            checkStmt.setInt(1, accountId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next())
            
            {
                System.out.println("Account not found or inactive.");
                return;
            }

            String currentName = rs.getString("name");
            System.out.println("Current Name: " + currentName);

            System.out.print("Enter New Name: ");
            String newName = scanner.nextLine().trim();

            if (newName.isEmpty())
            {
                System.out.println("Name cannot be empty.");
                return;
            }

            // Check if name is fully numeric
            boolean isNumeric = true;
            for (char c : newName.toCharArray()) 
            {
                if (!Character.isDigit(c)) 
                {
                    isNumeric = false;
                    break;
                }
            }

            if (isNumeric) 
            {
                System.out.println("Name cannot be numbers only.");
                return;
            }


            PreparedStatement updateStmt = connection.prepareStatement("UPDATE accounts SET name = ? WHERE id = ?");
            updateStmt.setString(1, newName);
            updateStmt.setInt(2, accountId);

            int rows = updateStmt.executeUpdate();
            if (rows > 0) 
            {
                System.out.println("Name updated successfully to: " + newName);
            } 
            else 
            {
                System.out.println("Failed to update name.");
            }

        } 
        catch (SQLException | NumberFormatException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
