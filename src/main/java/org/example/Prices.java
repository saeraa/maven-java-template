package org.example;

import java.util.ArrayList;
import java.util.Collections;

import static org.example.Menu.printMenu;
import static org.example.Menu.returnToMenu;

public class Prices {

    public static ArrayList<Integer> prices = new ArrayList<>();

    public static void checkPriceList() {
        if (prices.size() < 24) {
            System.out.println("Det finns inte tillräckligt många värden, försök igen.");
            prices.clear();
            printMenu();
        }
    }

    public static void minMaxMean() {

        checkPriceList();

        Integer minValue = Collections.min(prices);
        var minIndex = prices.indexOf(minValue);

        Integer maxValue = Collections.max(prices);
        var maxIndex = prices.indexOf(maxValue);

        var meanValue = findMeanValue();

        System.out.printf("""
                Det minsta värdet är %d mellan klockan %02d-%02d.
                Det största värdet är %d mellan klockan %02d-%02d.
                Medelvärdet är %d.
                
                """, minValue, minIndex, minIndex+1,
                maxValue, maxIndex, maxIndex+1,
                meanValue);

        returnToMenu();


    }

    public static void sortPrices() {

        checkPriceList();

        ArrayList<Integer> pricesCopy = new ArrayList<>(prices);
        int[] index = new int[prices.size()];

        Collections.sort(pricesCopy);
        final Integer min = Integer.MIN_VALUE;

        for (int i = 0; i < prices.size(); i++) {
            index[i] = prices.indexOf(pricesCopy.get(i));
            prices.set(prices.indexOf(pricesCopy.get(i)), min);
            System.out.printf("""
                    %02d-%02d: %d öre
                    """, index[i], index[i] + 1, pricesCopy.get(i));
        }

        returnToMenu();

    }

    public static void bestChargeTime() {

        checkPriceList();

        int index = 0;
        int sum = Integer.MAX_VALUE;

        for (int i = 0; i < 21; i++) {
            int tempSum = prices.get(i) + prices.get(i + 1) + prices.get(i + 2) + prices.get(i + 3);
            if (tempSum < sum) {
                sum = tempSum;
                index = i;
            }
        }

        System.out.printf("""
                        Det är billigast att börja ladda klockan %02d.
                        Då kommer det att kosta %d ören per timme.
                        """, index, sum / 4);

        returnToMenu();

    }

    private static int findMeanValue() {
        return prices.stream()
                .mapToInt(Integer::intValue)
                .sum() / 24;
    }
}
