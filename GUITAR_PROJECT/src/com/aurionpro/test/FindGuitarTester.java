package com.aurionpro.test;

import java.util.*;
import com.aurionpro.model.*;

public class FindGuitarTester {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) 
    {
        Inventory inventory = new Inventory();
        initializeInventory(inventory);

        boolean running = true;

        while (running)
        {
            try 
            {
                System.out.println("\n---- Guitar Inventory Menu ----");
                System.out.println("1. Search for Guitar");
                System.out.println("2. Add Guitar to Inventory");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine().trim();

                switch (choice) 
                {
                    case "1":
                        performSearch(inventory);
                        break;
                    case "2":
                        addGuitarToInventory(inventory);
                        break;
                    case "3":
                        System.out.println("Exiting program.");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice, Enter 1, 2 or 3.\n");
                }
            } 
            catch (Exception e) 
            {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void performSearch(Inventory inventory)
    {
        List<Guitar> currentResults = new ArrayList<>(inventory.getAllGuitars());
        Builder builder = null;
        String model = null;
        Type type = null;
        Wood backWood = null;
        Wood topWood = null;

        boolean filtering = true;
        Set<Integer> usedFilters = new HashSet<>();

        while (filtering)
        {
            System.out.println("\nCurrent matching guitars: " + currentResults.size());

            if (currentResults.isEmpty()) 
            {
                System.out.println("No matching guitars found.");
                return;
            }

            for (Guitar guitar : currentResults) 
            {
                GuitarSpec spec = guitar.getSpec();
                System.out.println("\nSerial: " + guitar.getSerialNumber());
                System.out.println("Builder: " + spec.getBuilder());
                System.out.println("Model: " + spec.getModel());
                System.out.println("Type: " + spec.getType());
                System.out.println("Back Wood: " + spec.getBackWood());
                System.out.println("Top Wood: " + spec.getTopWood());
                System.out.println("Price: Rs." + guitar.getPrice());
                System.out.println("---------------------------");
            }

            if (usedFilters.size() == 5) 
            {
                System.out.println("All filters applied.");
                break;
            }

            String continueResponse;
            while (true) 
            {
                System.out.print("\nDo you want to apply another filter? (y/n): ");
                continueResponse = scanner.nextLine().trim().toLowerCase();
                if (continueResponse.equals("y") || continueResponse.equals("n"))
                	break;
                else 
                	System.out.println("Please enter 'y' or 'n'.");
            }

            if (continueResponse.equals("n")) break;

            System.out.println("\nSelect a filter to apply:");
            if (!usedFilters.contains(1)) System.out.println("1. Builder");
            if (!usedFilters.contains(2)) System.out.println("2. Model");
            if (!usedFilters.contains(3)) System.out.println("3. Type");
            if (!usedFilters.contains(4)) System.out.println("4. Back Wood");
            if (!usedFilters.contains(5)) System.out.println("5. Top Wood");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (!usedFilters.contains(1)) 
                    {
                        builder = getEnumInput("Builder", Builder.class);
                        usedFilters.add(1);
                    }
                    break;
                case "2":
                    if (!usedFilters.contains(2)) 
                    {
                        model = getStringInput("Model");
                        usedFilters.add(2);
                    }
                    break;
                case "3":
                    if (!usedFilters.contains(3)) 
                    {
                        type = getEnumInput("Type", Type.class);
                        usedFilters.add(3);
                    }
                    break;
                case "4":
                    if (!usedFilters.contains(4)) 
                    {
                        backWood = getEnumInput("Back Wood", Wood.class);
                        usedFilters.add(4);
                    }
                    break;
                case "5":
                    if (!usedFilters.contains(5)) 
                    {
                        topWood = getEnumInput("Top Wood", Wood.class);
                        usedFilters.add(5);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Enter choice again");
                    continue;
            }

            GuitarSpec specFilter = new GuitarSpec(builder, model, type, backWood, topWood);
            currentResults = filterResults(currentResults, specFilter);
        }

        if (currentResults.isEmpty()) 
        {
            System.out.println("No matching guitars found.");
        } 
        else 
        {
            System.out.println("\nFinal matching guitars:");
            for (Guitar guitar : currentResults) 
            {
                GuitarSpec spec = guitar.getSpec();
                System.out.println("\nSerial: " + guitar.getSerialNumber());
                System.out.println("Builder: " + spec.getBuilder());
                System.out.println("Model: " + spec.getModel());
                System.out.println("Type: " + spec.getType());
                System.out.println("Back Wood: " + spec.getBackWood());
                System.out.println("Top Wood: " + spec.getTopWood());
                System.out.println("Price: Rs." + guitar.getPrice());
                System.out.println("---------------------------");
            }
        }
    }

    private static void addGuitarToInventory(Inventory inventory) 
    {
        System.out.println("\n--- Enter Guitar Details ---");

        String serialNumber = getStringInput("Serial Number");

        double price = 0;
        while (true)
        {
            System.out.print("Enter Price: ");
            try 
            {
                price = Double.parseDouble(scanner.nextLine().trim());
                if (price <= 0) 
                	throw new NumberFormatException();
                break;
            }
            catch (NumberFormatException e) 
            {
                System.out.println("Invalid price, Enter a positive number.");
            }
        }

        Builder builder = getEnumInput("Builder", Builder.class);
        String model = getStringInput("Model");
        Type type = getEnumInput("Type", Type.class);
        Wood backWood = getEnumInput("Back Wood", Wood.class);
        Wood topWood = getEnumInput("Top Wood", Wood.class);

        inventory.addGuitar(serialNumber, price, builder, model, type, backWood, topWood);
        System.out.println("Guitar added to inventory successfully!");
    }

    private static <T extends Enum<T>> T getEnumInput(String prompt, Class<T> enumType) 
    {
        T[] enumValues = enumType.getEnumConstants();

        while (true) 
        {
            try 
            {
                System.out.println("Select " + prompt + ":");
                for (int i = 0; i < enumValues.length; i++)
                {
                    System.out.println((i + 1) + ". " + enumValues[i].toString());
                }

                System.out.print("Enter your choice (1-" + enumValues.length + "): ");
                String input = scanner.nextLine().trim();

                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= enumValues.length) 
                {
                    return enumValues[choice - 1];
                }
                else 
                {
                    System.out.println("Invalid number, Enter between 1 and " + enumValues.length + ".\n");
                }
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Enter a valid number.\n");
            } 
            catch (Exception e) 
            {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static String getStringInput(String prompt) 
    {
        while (true) 
        {
            System.out.print("Enter " + prompt + ": ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) 
            {
            	return input.toLowerCase();
            }	
            System.out.println(prompt + " cannot be empty. Try again.\n");
        }
    }

    private static List<Guitar> filterResults(List<Guitar> guitars, GuitarSpec spec) 
    {
        List<Guitar> results = new ArrayList<>();
        for (Guitar g : guitars) 
        {
            if (spec.matches(g.getSpec()))
            {
                results.add(g);
            }
        }
        return results;
    }

    private static void initializeInventory(Inventory inventory)
    {
        inventory.addGuitar("V95693", 1499.95, Builder.FENDER, "stratocastor", Type.ELECTRIC, Wood.ALDER, Wood.ALDER);
        inventory.addGuitar("V9512", 1549.95, Builder.FENDER, "stratocastor", Type.ACOUSTIC, Wood.ALDER, Wood.ALDER);
        inventory.addGuitar("11277", 3999.95, Builder.COLLINGS, "cj", Type.ACOUSTIC, Wood.INDIAN_ROSEWOOD, Wood.SITKA);
        inventory.addGuitar("V12345", 1299.95, Builder.GIBSON, "les paul", Type.ELECTRIC, Wood.MAPLE, Wood.MAPLE);
        inventory.addGuitar("E45678", 1799.95, Builder.MARTIN, "d-28", Type.ACOUSTIC, Wood.BRAZILIAN_ROSEWOOD, Wood.ADIRONDACK);
        inventory.addGuitar("F32165", 2199.00, Builder.OLSON, "sj", Type.ACOUSTIC, Wood.CEDAR, Wood.COCOBOLO);
        inventory.addGuitar("G11223", 2499.99, Builder.RYAN, "signature", Type.ELECTRIC, Wood.MAHOGANY, Wood.MAPLE);
    }
}
