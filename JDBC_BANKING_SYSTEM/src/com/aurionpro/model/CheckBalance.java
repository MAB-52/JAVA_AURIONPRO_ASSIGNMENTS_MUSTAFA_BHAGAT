package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CheckBalance {

    public static void checkBalance(Scanner scanner) {
        try (Connection connection = JDBCConnection.getConnection()) 
        {
            System.out.print("Enter Account ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Check if account exists and is active
            if (!AccountValidator.isValidAccount(id)) 
            {
                System.out.println("Account not found or is inactive.");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(
                "SELECT balance FROM accounts WHERE id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) 
            {
                System.out.println("Current Balance: ₹" + resultSet.getDouble("balance"));
            } 
            else 
            {
                System.out.println("Account not found.");
            }

        } 
        catch (SQLException | NumberFormatException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
