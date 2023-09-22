package org.example;

public interface Discount {
    double apply(Product product);
    String getDescription(Product product);
}
