package org.example.functional;

import org.example.Product;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        FunctionalDiscount discountChain = discountChain();

        Product milk = new Product("Milk", 10, 3, LocalDate.of(2023, 9, 22));
        Product apples = new Product("Apples", 100, 6, LocalDate.of(2023, 9, 22));

        System.out.println("Purchase date for Apples: " + apples.getPurchaseDate());
        System.out.println("Day of week for Apples: " + apples.getPurchaseDate().getDayOfWeek());

        double milkDiscountedPrice = milk.getPrice() - discountChain.apply(milk);
        double applesDiscountedPrice = apples.getPrice() - discountChain.apply(apples);

        System.out.println("Discounted price for Milk: " + milkDiscountedPrice);
        System.out.println("Discounted price for Apples: " + applesDiscountedPrice);

    }

    public static FunctionalDiscount discountChain() {
        FunctionalDiscount milkDiscount = milkDiscount();
        FunctionalDiscount quantityDiscount = quantityDiscount();
        FunctionalDiscount fridayDiscount = fridayDiscount();

        milkDiscount.setNextDiscount(quantityDiscount);
        quantityDiscount.setNextDiscount(fridayDiscount);

        return milkDiscount;
    }

    public static FunctionalDiscount milkDiscount() {
        return new FunctionalDiscount(
            product -> product.getName().equalsIgnoreCase("milk"),
            product -> product.getPrice() * 0.05,
            " Milk is 5% off! \n"
    );
    }
    public static FunctionalDiscount fridayDiscount() {
            return new FunctionalDiscount(
            product -> product.getPurchaseDate().getDayOfWeek().equals(DayOfWeek.FRIDAY),
            product -> product.getPrice() * 0.10,
            " 10% off on Friyays! \n"
    );
    }

    public static FunctionalDiscount quantityDiscount() {
        return new FunctionalDiscount(
            product -> product.getQuantity() >= 5,
            product -> product.getQuantity() * 10.0,
            " Quantity discount: 10kr discount per product for buying at least 5 products! \n"
    );
    }
}
