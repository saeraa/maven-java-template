package org.example.functional;

import org.example.Product;

import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalDiscount {
    private final Predicate<Product> isApplicable;
    private final Function<Product, Double> calculateDiscount;
    private final String description;
    private FunctionalDiscount nextDiscount;

    public FunctionalDiscount(Predicate<Product> isApplicable, Function<Product, Double> calculateDiscount, String description) {
        this.isApplicable = isApplicable;
        this.calculateDiscount = calculateDiscount;
        this.description = description;
    }

    public FunctionalDiscount setNextDiscount(FunctionalDiscount nextDiscount) {
        this.nextDiscount = nextDiscount;
        return this;
    }

    public double apply(Product product) {
        double discount = 0;
        if (isApplicable.test(product)) {
            discount += calculateDiscount.apply(product);
        }
        if (nextDiscount != null) {
            discount += nextDiscount.apply(product);
        }
        return discount;
    }

    public String getDescription() {
        return description;
    }

}