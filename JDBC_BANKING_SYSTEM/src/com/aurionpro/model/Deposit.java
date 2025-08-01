package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Deposit {

    public static void deposit(Scanner scanner) 
    {
        try (Connection connection = JDBCConnection.getConnection()) 
        {
            connection.setAutoCommit(false);

            System.out.print("Enter Account ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (!AccountValidator.isValidAccount(id)) 
            {
                System.out.println("Account not found.");
                return;
            }

            System.out.print("Enter Deposit Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0)
            {
                throw new IllegalArgumentException("Amount must be positive.");
            }

            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
            statement.setDouble(1, amount);
            statement.setInt(2, id);

            int rows = statement.executeUpdate();
            if (rows == 0) 
            {
                System.out.println("Failed to deposit.");
                connection.rollback();
            } 
            else 
            {
                connection.commit();
                TransactionLogger.logTransaction(id, "DEPOSIT", amount, "Amount deposited");
                System.out.println("Rs. " + amount + " deposited successfully.");
            }

        } 
        catch (SQLException | IllegalArgumentException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
