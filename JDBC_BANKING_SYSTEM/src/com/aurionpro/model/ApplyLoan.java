package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplyLoan {

    public static void applyLoan(int accountId, double loanAmount) throws SQLException, ClassNotFoundException {
        if (loanAmount <= 0)
        {
            throw new IllegalArgumentException("Loan amount must be positive.");
        }

        if (!AccountValidator.isValidAccount(accountId))
        {
            System.out.println("Account not found or it is inactive.");
            return;
        }

        if (loanAmount > 10000) 
        {
            System.out.println("Loan request denied. Maximum loan limit is Rs. 10000.");
            return;
        }

        try (Connection connection = JDBCConnection.getConnection()) 
        {
            PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT balance, loan FROM accounts WHERE id = ?");
            checkStatement.setInt(1, accountId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next())
            {
                double balance = resultSet.getDouble("balance");
                double currentLoan = resultSet.getDouble("loan");

                if (balance <= 1000)
                {
                    System.out.println("Loan request denied. Minimum balance of ₹1000 is required to apply for a loan.");
                    return;
                }

                if (currentLoan + loanAmount > 10000) 
                {
                    System.out.println("Loan request denied. Total loan cannot exceed Rs. 10000.");
                    return;
                }

                PreparedStatement loanStatement = connection.prepareStatement(
                        "UPDATE accounts SET balance = balance + ?, loan = loan + ? WHERE id = ?");
                loanStatement.setDouble(1, loanAmount);
                loanStatement.setDouble(2, loanAmount);
                loanStatement.setInt(3, accountId);

                int rows = loanStatement.executeUpdate();
                if (rows > 0)
                {
                    // Log the loan transaction
                    TransactionLogger.logTransaction(accountId, "LOAN", loanAmount, "Loan granted");

                    System.out.println("Loan of ₹" + loanAmount + " granted successfully to account ID " + accountId + ".");
                } 
                else 
                {
                    System.out.println("Loan update failed.");
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error while applying for loan: " + e.getMessage());
        }
    }
}
