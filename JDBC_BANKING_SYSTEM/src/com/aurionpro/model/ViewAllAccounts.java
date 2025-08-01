package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewAllAccounts {

    public static void viewAllAccounts(Scanner scanner) {
        // Authenticate admin before viewing....
        if (!Admin.authenticate(scanner)) 
        {
            System.out.println("Invalid Admin Credentials.");
            return;
        }

        try (Connection connection = JDBCConnection.getConnection()) 
        {
            String query = "SELECT id, name, balance, loan, status FROM accounts ORDER BY id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n===== All Bank Accounts =====");
            System.out.printf("%-10s %-15s %-15s %-10s %-10s\n", "ID", "Name", "Balance", "Loan", "Status");
            System.out.println("---------------------------------------------------------------");

            boolean found = false;
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double balance = resultSet.getDouble("balance");
                double loan = resultSet.getDouble("loan");
                String status = resultSet.getString("status");

                System.out.printf("%-10d %-20s ₹%-14.2f ₹%-10.2f %-10s\n", id, name, balance, loan, status);
                found = true;
            }

            if (!found) 
            {
                System.out.println("No accounts found.");
            }

        } 
        catch (SQLException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
