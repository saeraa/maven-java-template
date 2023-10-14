package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductRecord;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@ApplicationScoped
public class Warehouse implements WarehouseService {

    private final List<Product> products = new CopyOnWriteArrayList<>();
    private final int maxRating = 10;

    public ProductRecord addProduct(@Valid ProductRecord product) {
      if (product.name() == null || product.name().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty when adding products");
        }

        Product newProduct = new Product(product);
        products.add(newProduct);

        return newProduct.toRecord();
    }

    public Optional<ProductRecord> modifyProduct(@Valid ProductRecord productInput) {
        return products.stream().filter(p -> p.getId().equals(productInput.id())).findFirst()
                .map(product -> {
                    product.setModifiedBy(LocalDate.now());
                    product.setCategory(productInput.category());
                    product.setRating(productInput.rating());
                    product.setName(productInput.name());
                    return product.toRecord();
                });
    }

    public List<ProductRecord> getAllProducts() {
        return products.stream()
                .map(Product::toRecord)
                .toList();
    }

    public ProductRecord getProductById(String id) {
        return products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("ID not found"))
                .toRecord();
    }

    public List<ProductRecord> getProductsByCategory(Category category) {
        return products.stream()
                .filter(product -> product.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .map(Product::toRecord)
                .toList();
    }

    public List<ProductRecord> getProductsAfterDate(LocalDate date) {
        return products.stream()
                .filter(product -> product.getCreatedBy().isAfter(date))
                .map(Product::toRecord)
                .toList();
    }

    public List<ProductRecord> getModifiedProducts() {
        return products.stream()
                .filter(product -> !product.getCreatedBy().equals(product.getModifiedBy()))
                .map(Product::toRecord)
                .toList();
    }

    public List<Category> getCategoriesWithProducts() {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory))
                .keySet()
                .stream()
                .toList();
    }
    public List<String> getProductsForCategory(Category category) {
        return products.stream()
                .filter(product -> product.getCategory().equals(category))
                .map(Product::getName)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    public int getAmountOfProductsForCategory(Category category) {
        return products.stream()
                .filter(product -> product.getCategory().equals(category))
                .map(Product::toRecord)
                .toList()
                .size();
    }

    public List<ProductRecord> getProductsWithMaxRating() {
        return products.stream()
                .filter(p -> p.getRating() == maxRating)
                .filter(p -> p.getCreatedBy()
                        .isBefore(LocalDate.now().plusDays(15))
                        && p.getCreatedBy()
                        .isAfter(LocalDate.now().minusDays(15)))
                .sorted(Comparator.comparing(Product::getCreatedBy).reversed())
                .map(Product::toRecord)
                .toList();
    }

    public Map<String, Long> getProductsMappedByFirstLetter() {
        return products.stream()
                .collect(Collectors
                        .groupingByConcurrent(product -> product.getName().substring(0,1),
                                Collectors.counting()));
    }
}
