package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionLogger
{

    public static void logTransaction(int accountId, String type, double amount, String description) 
    {
    	
        try (Connection connection = JDBCConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO transactions (account_id, type, amount, description, timestamp) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, accountId);
            statement.setString(2, type);
            statement.setDouble(3, amount);
            statement.setString(4, description);
            statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Transaction log error: " + e.getMessage());
        }
    }
}
