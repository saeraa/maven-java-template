package org.example;

import java.time.LocalDate;

public class Product {

    private String name;
    private double price;
    private int quantity;
    private LocalDate purchaseDate;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.purchaseDate = LocalDate.now();
    }

    public Product(String name, double price, int quantity, LocalDate purchaseDate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
