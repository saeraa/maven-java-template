package org.example;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        Product milk = new Product("Milk", 20, 3, LocalDate.of(2023, 9, 22));
        Product apples = new Product("Apples", 100, 6, LocalDate.of(2023, 9, 22));
        Discount discountChain = new QuantityDiscount(new MilkDiscount(new FridayDiscount(new NoDiscount())));

        System.out.println("Buying some milk!");
        double milkDiscountedPrice = milk.getPrice() - discountChain.apply(milk);
        System.out.println("Discounted price for Milk: " + milkDiscountedPrice);
        System.out.println("Discounts applied: ");
        System.out.println(discountChain.getDescription(milk));

        System.out.println("Buying some apples!");
        double applesDiscountedPrice = apples.getPrice() - discountChain.apply(apples);
        System.out.println("Discounted price for Apples: " + applesDiscountedPrice);
        System.out.println("Discounts applied: ");
        System.out.println(discountChain.getDescription(apples));
    }
}
