package org.example.service;

import jakarta.ejb.Singleton;
import jakarta.validation.Valid;
import org.example.entities.Category;
import org.example.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Singleton
public interface WarehouseService {

    ProductRecord addProduct(@Valid ProductRecord product);
    Optional<ProductRecord> modifyProduct(@Valid ProductRecord productInput);
    List<ProductRecord> getAllProducts();
    ProductRecord getProductById(String id);
    List<ProductRecord> getProductsByCategory(Category category);
    List<ProductRecord> getProductsAfterDate(LocalDate date);
    List<ProductRecord> getModifiedProducts();
    List<Category> getCategoriesWithProducts();
    List<String> getProductsForCategory(Category category);
    int getAmountOfProductsForCategory(Category category);
    List<ProductRecord> getProductsWithMaxRating();
    Map<String, Long> getProductsMappedByFirstLetter();
}
