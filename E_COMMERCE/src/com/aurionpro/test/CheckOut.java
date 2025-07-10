package com.aurionpro.test;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.aurionpro.model.CreditCardPayment;
import com.aurionpro.model.IPaymentGateway;
import com.aurionpro.model.NetBanking;
import com.aurionpro.model.UPIPayment;

public class CheckOut {

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        IPaymentGateway paymentGateway = null;
        double amount;
        double balance = 10000.0;

        try
        {
            while (true) 
            {
                try 
                {
                    System.out.println("\n===== Payment Method Selection =====");
                    System.out.printf("Your current balance: ₹%.2f\n", balance);
                    System.out.println("1 - Credit Card Payment");
                    System.out.println("2 - UPI Payment");
                    System.out.println("3 - Net Banking Payment");
                    System.out.println("4 - Exit Program");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();

                    if (choice == 4)
                    {
                        System.out.println("Program exited. Thank you!");
                        break;
                    }

                    if (choice < 1 || choice > 4) 
                    {
                        System.out.println("Invalid choice. Please select from 1–4.");
                        continue;
                    }

                    switch (choice) 
                    {
                        case 1:
                            paymentGateway = new CreditCardPayment();
                            System.out.println("You selected Credit Card Payment");
                            break;
                        case 2:
                            paymentGateway = new UPIPayment();
                            System.out.println("You selected UPI Payment");
                            break;
                        case 3:
                            paymentGateway = new NetBanking();
                            System.out.println("You selected Net Banking Payment");
                            break;
                    }

                    while (true) 
                    {
                        try
                        {
                            System.out.print("Enter amount to pay (or 0 to return to main menu): ");
                            amount = scanner.nextDouble();

                            if (amount == 0) 
                            {
                                scanner.nextLine(); 
                                System.out.print("You entered ₹0. Return to main menu? (yes/no): ");
                                String confirm = scanner.nextLine().trim().toLowerCase();
                                if (confirm.equals("yes"))
                                {
                                    System.out.println("Returning to main menu...");
                                    break;
                                } 
                                else 
                                {
                                    System.out.println("Staying in current payment method.");
                                    continue;
                                }
                            }

                            if (amount < 0)
                            {
                                System.out.println("Amount must be positive. Try again.");
                                continue;
                            }

                            if (amount > balance) 
                            {
                                System.out.printf("Insufficient balance. You only have ₹%.2f\n", balance);
                                continue;
                            }

                            // Process payment
                            paymentGateway.pay(amount);
                            balance -= amount;

                            // Cashback logic
                            switch (choice) 
                            {
                                case 1: // Credit Card
                                    double ccRefund = (amount * 5) / 100;
                                    System.out.printf("Cashback upto 5%% (₹%.2f)\n", ccRefund);
                                    paymentGateway.refund(ccRefund);
                                    balance += ccRefund;
                                    break;

                                case 2: // UPI
                                    double upiRefund = 100;
                                    System.out.println("Cashback of flat ₹100...");
                                    paymentGateway.refund(upiRefund);
                                    balance += upiRefund;
                                    break;

                                case 3: // Net Banking
                                    double nbRefund = (amount * 8) / 100;
                                    System.out.printf("Cashback upto 8%% (₹%.2f)...\n", nbRefund);
                                    paymentGateway.refund(nbRefund);
                                    balance += nbRefund;
                                    break;
                            }

                            System.out.printf("Updated balance: ₹%.2f\n", balance);
                        } 
                        catch (InputMismatchException e) 
                        {
                            System.out.println("Invalid input. Please enter a valid numeric amount.");
                            scanner.next(); 
                        } 
                        catch (Exception e)
                        {
                            System.out.println("Unexpected error during payment: " + e.getMessage());
                        }
                    }

                } 
                catch (InputMismatchException e)
                {
                    System.out.println("Invalid menu input. Please enter a number between 1 and 4.");
                    scanner.next(); 
                } 
                catch (Exception e)
                {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
            }
        } 
        finally 
        {
            scanner.close();
            System.out.println("Program ended.");
        }
    }
}
