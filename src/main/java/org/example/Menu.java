package org.example;

import static org.example.App.scanner;
import static org.example.Input.readFromFile;
import static org.example.Input.readFromUserInput;
import static org.example.Prices.*;

public class Menu {

    public static void menuChoice() {

        System.out.println("Välj ett menyval: ");
        var choice = scanner.nextLine();

        switch (choice.toLowerCase()) {
            case "1" -> {
                System.out.println("1. Inmatning");
                readFromUserInput();
            }
            case "2" -> {
                System.out.println("2. Min, Max och Medel");
                minMaxMean();
            }
            case "3" -> {
                System.out.println("3. Sortera");
                sortPrices();
            }
            case "4" -> {
                System.out.println("4. Bästa Laddningstid (4h)");
                bestChargeTime();
            }
            case "5" -> {
                System.out.println("5. Inmatning från fil");
                readFromFile();
            }
            case "e" -> {
                System.out.println("Avslutar programmet..");
                System.exit(0);
            }
            default -> {
                System.out.println("Ogiltigt menyval, försök med något annat.\n");
                printMenu();
            }
        }

        scanner.close();
    }

    public static void printMenu() {
        System.out.println("""
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Inmatning från fil
                e. Avsluta
                """);

        menuChoice();
    }

    public static void returnToMenu() {
        System.out.println("Vill du gå tillbaka till menyn? j/n");

        var choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("j")) {
            printMenu();
        } else if (choice.equalsIgnoreCase("n")) {
            System.exit(0);
        } else {
            System.out.println("Felaktig inmatning. Försök igen.");
            returnToMenu();
        }
    }
}
