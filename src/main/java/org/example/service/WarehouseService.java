package org.example.service;

import org.example.entities.Category;
import org.example.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WarehouseService {

    public ProductRecord addProduct(ProductRecord product);
    public ProductRecord modifyProduct(ProductRecord productInput);
    public List<ProductRecord> getAllProducts();
    public ProductRecord getProductById(String id);
    public List<ProductRecord> getProductsByCategory(Category category);
    public List<ProductRecord> getProductsAfterDate(LocalDate date);
    public List<ProductRecord> getModifiedProducts();
    public List<Category> getCategoriesWithProducts();
    public List<String> getProductsForCategory(Category category);
    public int getAmountOfProductsForCategory(Category category);
    public List<ProductRecord> getProductsWithMaxRating();
    public Map<String, Long> getProductsMappedByFirstLetter();
}
