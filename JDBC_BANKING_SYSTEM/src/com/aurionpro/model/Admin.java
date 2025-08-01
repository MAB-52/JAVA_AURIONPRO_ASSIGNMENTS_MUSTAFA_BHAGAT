package com.aurionpro.model;

import java.util.Scanner;

public class Admin {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    public static boolean authenticate(Scanner scanner)
    {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
