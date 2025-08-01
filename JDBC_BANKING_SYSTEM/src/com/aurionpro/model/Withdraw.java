package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Withdraw {

    public static void withdraw(Scanner scanner)
    {
        try (Connection connection = JDBCConnection.getConnection())
        {
            connection.setAutoCommit(false);

            System.out.print("Enter Account ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (!AccountValidator.isValidAccount(id))
            {
            	System.out.println("Account not found or it is inactive.");
                return;
            }

            System.out.print("Enter Withdraw Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0)
            {
                throw new IllegalArgumentException("Amount must be positive.");
            }

            PreparedStatement checkStatement = connection.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
            checkStatement.setInt(1, id);
            ResultSet resultSet = checkStatement.executeQuery();

            resultSet.next();
            double balance = resultSet.getDouble("balance");

            if (balance < amount)
            {
                System.out.println("Insufficient balance.");
                connection.rollback();
                return;
            }

            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
            statement.setDouble(1, amount);
            statement.setInt(2, id);
            statement.executeUpdate();

            connection.commit();
            TransactionLogger.logTransaction(id, "WITHDRAW", amount, "Amount withdrawn");
            System.out.println("Rs. " + amount + " withdrawn successfully.");

        } 
        catch (SQLException | IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
