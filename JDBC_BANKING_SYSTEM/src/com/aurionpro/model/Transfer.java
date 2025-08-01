package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Transfer {

    public static void transfer(Scanner scanner) {
        try (Connection connection = JDBCConnection.getConnection()) {
            connection.setAutoCommit(false);

            System.out.print("Enter Sender Account ID: ");
            int senderId = Integer.parseInt(scanner.nextLine());

            if (!AccountValidator.isValidAccount(senderId)) 
            {
                System.out.println("Sender account not found or inactive.");
                return;
            }

            System.out.print("Enter Receiver Account ID: ");
            int receiverId = Integer.parseInt(scanner.nextLine());

            if (!AccountValidator.isValidAccount(receiverId))
            {
                System.out.println("Receiver account not found or inactive.");
                return;
            }

            System.out.print("Enter Amount to Transfer: ");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0)
            {
                throw new IllegalArgumentException("Amount must be positive.");
            }

            // Check if both accounts are Active
            if (!isAccountActive(connection, senderId) || !isAccountActive(connection, receiverId)) 
            {
                System.out.println("Transfer failed. One or both accounts are inactive.");
                return;
            }

            // Get sender balance
            double senderBalance = getAccountBalance(connection, senderId);
            if (senderBalance < amount)
            {
                System.out.println("Insufficient balance.");
                connection.rollback();
                return;
            }

            if (senderId == receiverId) 
            {
                // Self-transfer case
                System.out.println("Self-transfer detected. Processing internal transfer...");
            }

            // Debit sender
            PreparedStatement debit = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
            debit.setDouble(1, amount);
            debit.setInt(2, senderId);
            debit.executeUpdate();

            // Credit receiver
            PreparedStatement credit = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
            credit.setDouble(1, amount);
            credit.setInt(2, receiverId);
            credit.executeUpdate();

            // Commit transaction
            connection.commit();

            // Log transactions
            String senderMsg = (senderId == receiverId) ? "Self-transfer" : "Transferred to Account ID " + receiverId;
            String receiverMsg = (senderId == receiverId) ? "Self-transfer" : "Received from Account ID " + senderId;

            TransactionLogger.logTransaction(senderId, "TRANSFER OUT", amount, senderMsg);
            TransactionLogger.logTransaction(receiverId, "TRANSFER IN", amount, receiverMsg);

            System.out.println("Rs. " + amount + " transferred successfully from " + senderId + " to " + receiverId);

        } 
        catch (SQLException | IllegalArgumentException e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean isAccountActive(Connection connection, int accountId) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement("SELECT status FROM accounts WHERE id = ?");
        stmt.setInt(1, accountId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next())
        {
            return "Active".equalsIgnoreCase(rs.getString("status"));
        }
        return false;
    }

    private static double getAccountBalance(Connection connection, int accountId) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
        stmt.setInt(1, accountId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) 
        {
            return rs.getDouble("balance");
        }
        return 0;
    }
}
