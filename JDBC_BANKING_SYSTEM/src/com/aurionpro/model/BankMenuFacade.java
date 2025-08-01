package com.aurionpro.model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankMenuFacade {

    public void start()
    {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("================================");
        System.out.println("       JDBC BANKING SYSTEM    ");
        System.out.println("================================");

        while (true) 
        {
            try 
            {
                System.out.println("\n======= BANK MENU =======");
                System.out.println("1. Open Account");
                System.out.println("2. Check Balance");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. Transfer");
                System.out.println("6. Activate or Deactivate Account");
                System.out.println("7. View All Accounts");
                System.out.println("8. Apply for Loan");
                System.out.println("9. View Transaction History"); 
                System.out.println("10. Update Account Holder Name");
                System.out.println("11. Exit");
                System.out.print("Enter your choice (1-11): ");

                String input = scanner.nextLine().trim();
                if (!input.matches("\\d+")) 
                {
                    throw new InputMismatchException("Please enter a valid numeric choice.");
                }

                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        OpenAccount.openAccount(scanner);
                        break;
                    case 2:
                    	CheckBalance.checkBalance(scanner);
                        break;
                    case 3:
                        Deposit.deposit(scanner);
                        break;
                    case 4:
                        Withdraw.withdraw(scanner);
                        break;
                    case 5:
                        Transfer.transfer(scanner);
                        break;
                    case 6:
                    	CloseAccount.closeOrActivateAccount(scanner);
                        break;   
                    case 7:
                    	ViewAllAccounts.viewAllAccounts(scanner);
                        break;
                    case 8:
                        System.out.print("Account ID: ");
                        int loanId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter loan amount: ");
                        double loanAmt = scanner.nextDouble();
                        scanner.nextLine();
                        ApplyLoan.applyLoan(loanId, loanAmt);
                        break;
                    case 9:
                        TransactionHistory.viewHistory(scanner);
                        break;
                    case 10:
                        UpdateAccount.updateAccountName(scanner);
                        break;
                    case 11:
                        System.out.println("Exiting the program....");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Select a number between 1 to 11.");
                }

            }
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input: " + e.getMessage());
            } 
            catch (Exception e) 
            {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}
