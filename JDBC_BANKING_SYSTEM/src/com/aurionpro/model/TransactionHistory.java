package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TransactionHistory {

    public static void viewHistory(Scanner scanner) {
        try (Connection connection = JDBCConnection.getConnection()) 
        {
            System.out.print("Enter Account ID to view transaction history: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (!AccountValidator.isValidAccount(id)) 
            {
                System.out.println("Account not found or it is inactive.");
                return;
            }

            String query = "SELECT type, amount, description, timestamp FROM transactions WHERE account_id = ? ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            System.out.println("\n===== Transaction History for Account ID " + id + " =====");
            System.out.printf("%-20s %-10s %-30s %-20s\n", "Type", "Amount", "Description", "Timestamp");
            System.out.println("------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) 
            {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String desc = rs.getString("description");
                String time = rs.getString("timestamp");

                System.out.printf("%-20s ₹%-9.2f %-30s %-20s\n", type, amount, desc, time);
                found = true;
            }

            if (!found) 
            {
                System.out.println("No transactions found for this account.");
            }

        } 
        catch (Exception e) 
        {
            System.out.println("Error fetching transaction history: " + e.getMessage());
        }
    }
}
