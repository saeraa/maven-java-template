package org.example.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Product {

    private final String id;

    @NotEmpty(message = "Product name must not be empty")
    @Pattern(regexp = "[a-z-A-Z]*", message = "Product name has invalid characters")
    private String name;

    @Min(value=1, message = "Minimum rating is 1")
    @Max(value = 10, message = "Maximum rating is 10")
    private int rating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "CreatedBy date must be past or present")
    private final LocalDate createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "ModifiedBy date must be future or present")
    private LocalDate modifiedBy;
    private Category category;

    public Product() {
        this(null, null);
    }

    public Product(String id, LocalDate createdBy) {
        this.id = "id";
        this.createdBy = LocalDate.now();
    }

    public Product(ProductRecord product) {
        this.id = product.id();
        this.name = product.name();
        this.rating = product.rating();
        this.createdBy = product.createdBy();
        this.modifiedBy = product.modifiedBy();
        this.category = product.category();
    }

    public Product(String name, int rating, LocalDate createdBy, LocalDate modifiedBy, Category category) {
        this.id = String.valueOf(UUID.randomUUID());
        this.name = name;
        this.rating = rating;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.category = category;
    }

    public Product(String name, int rating, Category category) {
        this.id = String.valueOf(UUID.randomUUID());

        this.name = name;
        this.rating = rating;
        this.createdBy = LocalDate.now();
        this.modifiedBy = LocalDate.now();
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public LocalDate getCreatedBy() {
        return createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(LocalDate modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Product other = (Product) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", createdBy=" + createdBy +
                ", modifiedBy=" + modifiedBy +
                ", category=" + category +
                '}';
    }

    public ProductRecord toRecord() {
        return new ProductRecord(id, name, rating, createdBy, modifiedBy, category);
    }

}

