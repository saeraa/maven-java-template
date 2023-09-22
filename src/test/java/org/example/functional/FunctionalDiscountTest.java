package org.example.functional;

import org.example.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.example.functional.App.discountChain;
import static org.example.functional.App.fridayDiscount;
import static org.junit.jupiter.api.Assertions.*;

class FunctionalDiscountTest {

    public static FunctionalDiscount discountChain;

    @BeforeAll
    static void setup() {
        discountChain = discountChain();
    }

    @Test
    void whenBuyingFridayApplyFridayDiscount() {
        Product apples = new Product("Apples", 10, 1, LocalDate.of(2023,9,22));

        var actualDiscount = discountChain.apply(apples);
        var fridayDiscount = apples.getPrice() * 0.10;

        assertEquals(fridayDiscount, actualDiscount);
    }

    @Test
    void whenBuyingThursdayApplyNoDiscount() {
        Product apples = new Product("Apples", 10, 1, LocalDate.of(2023,9,21));

        assertEquals(DayOfWeek.THURSDAY, apples.getPurchaseDate().getDayOfWeek());

        var actualDiscount = discountChain.apply(apples);
        var fridayDiscount = apples.getPrice() * 0.10;

        assertNotEquals(fridayDiscount, actualDiscount);
        assertEquals(0, actualDiscount);
    }

    @Test
    void whenBuyingMilkApplyMilkDiscount() {
        Product milk = new Product("MiLk", 100, 2, LocalDate.of(2023,9,21));
        var actualDiscount = discountChain.apply(milk);
        var milkDiscount = milk.getPrice() * 0.05;

        assertEquals(milkDiscount, actualDiscount);
    }

    @Test
    void whenBuyingManyItemsApplyQuantityDiscount() {
        Product coffee = new Product("Coffee", 100, 6, LocalDate.of(2023,9,21));
        var actualDiscount = discountChain.apply(coffee);
        var quantityDiscount = coffee.getQuantity() * 10;

        assertEquals(quantityDiscount, actualDiscount);
    }

    @Test
    void getDescription() {
        var fridayDescription = fridayDiscount().getDescription();
        assertTrue(fridayDescription.contains("Friyay"));
    }
}