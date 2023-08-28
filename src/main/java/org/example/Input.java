package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.example.App.scanner;
import static org.example.Menu.printMenu;
import static org.example.Prices.checkPriceList;
import static org.example.Prices.prices;

public class Input {

    public static int checkInt(String input) {

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Fel inputvärde, försök igen.");
        }
        return 0;

    }


    public static void readFromFile() {

        File file = new File("priser.csv");

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                String[] values = data.split(",");

                for (String value : values) {
                    var currVal = checkInt(value.trim());
                    prices.add(currVal);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Filen kunde inte hittas.");
        }

        checkPriceList();

        printMenu();


    }

    public static void readFromUserInput() {

        if (!prices.isEmpty()) {
            prices.clear();
        }

        for (int i = 0; i < 24; i++) {
        System.out.printf("""
            Fyll i priset för följande klockslag:
            %02d-%02d
            """, i, i+1);

        var input = scanner.nextLine();

        if (input.equalsIgnoreCase("e")) {
            System.exit(0);
        }

        var checkedInput = checkInt(input);

        if (checkedInput > 0) prices.add(checkedInput);
        else i--;

        }

        System.out.println("Tack för värdena.");
        printMenu();
    }
}
