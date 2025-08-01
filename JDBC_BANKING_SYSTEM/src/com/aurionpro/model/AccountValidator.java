package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountValidator {

    public static boolean accountExists(int id) {
        try (Connection connection = JDBCConnection.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM accounts WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } 
        catch (SQLException e) 
        {
            System.out.println("Validation Error: " + e.getMessage());
            return false;
        }
    }

    public static String getAccountStatus(int id) 
    {
        try (Connection connection = JDBCConnection.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT status FROM accounts WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) 
            {
                return resultSet.getString("status");
            }
        } 
        catch (SQLException e)
        {
            System.out.println("Error fetching account status: " + e.getMessage());
        }
        return null;
    }

    public static boolean isValidAccount(int id) 
    {
        return accountExists(id) && isAccountActive(id);
    }

    public static boolean isAccountActive(int id) 
    {
        String status = getAccountStatus(id);
        return status != null && status.equalsIgnoreCase("Active");
    }
}
