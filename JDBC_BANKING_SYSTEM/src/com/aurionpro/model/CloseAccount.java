package com.aurionpro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CloseAccount {

    public static void closeOrActivateAccount(Scanner scanner) {
        try (Connection connection = JDBCConnection.getConnection())
        {
            System.out.print("Enter Account ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Validate account existence
            if (!AccountValidator.accountExists(id)) 
            {
                System.out.println("Account not found.");
                return;
            }

            //  Fetch current status
            String currentStatus = AccountValidator.getAccountStatus(id);
            System.out.println("Current Status: " + currentStatus);

            // Menu
            System.out.println("\nChoose Operation:");
            System.out.println("1. Deactivate Account");
            System.out.println("2. Activate Account");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            String newStatus = null;

            if (choice == 1 && currentStatus.equalsIgnoreCase("Active")) 
            {
                newStatus = "Inactive";
            } 
            else if (choice == 2 && currentStatus.equalsIgnoreCase("Inactive")) 
            {
                newStatus = "Active";
            } 
            else 
            {
                System.out.println("Operation not applicable. Account is already in the desired status.");
                return;
            }

            //Update status
            PreparedStatement updateStmt = connection.prepareStatement(
                "UPDATE accounts SET status = ? WHERE id = ?"
            );
            updateStmt.setString(1, newStatus);
            updateStmt.setInt(2, id);

            int rows = updateStmt.executeUpdate();
            if (rows > 0)
            {
                System.out.println("Account ID " + id + " status updated to: " + newStatus);
            } 
            else 
            {
                System.out.println("Failed to update account status.");
            }

        } 
        catch (SQLException | NumberFormatException e)
        {
            System.out.println("Error occured: " + e.getMessage());
        }
    }
}
