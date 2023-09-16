package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private Warehouse warehouse;

    @BeforeEach
    void setup() {
        warehouse = new Warehouse();
    }

    /*
    * Tests using their own lists first;
    * need to either add to the list or modify product items
    *  */

    @Test
    void addProduct()  {
        Product product = new Product("Laptop", 8, Category.ELECTRONICS);
        warehouse.addProduct(product.toRecord());
        assertEquals("Laptop", warehouse.getAllProducts().get(0).name());
    }

    @Test
    void addProductWithNoNameThenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product("", 8, Category.ELECTRONICS);
            warehouse.addProduct(product.toRecord());
        });

        String expectedMessage = "Name cannot be empty when adding products";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void modifyProductThenProductChanged() {
        Product product = new Product("Laptop", 8, Category.ELECTRONICS);
        warehouse.addProduct(product.toRecord());

        ProductRecord modifyProduct = new ProductRecord(product.getId(), "Laptop", 8, LocalDate.now(), LocalDate.now(), Category.FOOD);

        ProductRecord modifiedProduct = warehouse.modifyProduct(modifyProduct);

        assertEquals(Category.FOOD, modifiedProduct.category());
    }

    @Test
    void modifyProductWithFalseProductIdThenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProductRecord falseProduct = new ProductRecord("False_product_id", "Laptop", 8, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);
            warehouse.modifyProduct(falseProduct);
        });

        String expectedMessage = "Product not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getAllProductsThenCheckListSize() {
        Product product1 = new Product("Book", 7, Category.BOOKS);
        Product product2 = new Product("Phone", 9, Category.ELECTRONICS);

        warehouse.addProduct(product1.toRecord());
        warehouse.addProduct(product2.toRecord());

        List<ProductRecord> allProducts = warehouse.getAllProducts();
        assertEquals(2, allProducts.size());
    }

    @Test
    void getAllProductsWhenThereAreNone() {
        List<ProductRecord> allProducts = warehouse.getAllProducts();
        assertEquals(0, allProducts.size());
    }

    @Test
    void getProductById() {
        Product product = new Product("Pants", 6, Category.CLOTHING);
        warehouse.addProduct(product.toRecord());
        ProductRecord fetchedProduct = warehouse.getProductById(product.getId());
        assertEquals("Pants", fetchedProduct.name());
    }

    @Test
    void getProductByWrongId() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> warehouse.getProductById("FALSE_ID"));

        String expectedMessage = "ID not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /*
    * Nested testclass using a "global" list of products for retrieving
    * specific items or information
    * */

    @Nested
    public class WarehouseTestUsingOneList {

        private static List<Product> productList;
        private static final Warehouse warehouse = new Warehouse();

        @BeforeAll
        static void setup() {

            productList = List.of(
                    new Product("Banana", 8,
                            LocalDate.now(), LocalDate.now(), Category.FOOD),
                    new Product("Apple", 7,
                            LocalDate.now(), LocalDate.now(), Category.FOOD),
                    new Product( "Tome", 1,
                            LocalDate.now(), LocalDate.now(), Category.BOOKS),
                    new Product("Pants", 10,
                            LocalDate.now(), LocalDate.now(), Category.CLOTHING),
                    new Product("Book", 7,
                            LocalDate.now(), LocalDate.now(), Category.BOOKS),
                    new Product("Phone", 10,
                            LocalDate.now().minusDays(1), LocalDate.now(), Category.ELECTRONICS),
                    new Product("Laptop", 7, LocalDate.now().plusDays(10),
                            LocalDate.now().plusDays(10), Category.ELECTRONICS),
                    new Product("Camera", 8, LocalDate.now().plusDays(1),
                            LocalDate.now().plusDays(1), Category.ELECTRONICS)
                );
            productList.forEach(item -> warehouse.addProduct(item.toRecord()));
        }

        @Test
        void getProductsByCategory() {
            List<ProductRecord> foodProducts = warehouse.getProductsByCategory(Category.FOOD)
                    .stream()
                    .sorted(Comparator.comparing(ProductRecord::name))
                    .toList();

            assertEquals(2, foodProducts.size());
            assertEquals("Apple", foodProducts.get(0).name());
        }

        @Test
        void getProductsByCategoryWhenThereAreNone() {
            List<ProductRecord> allProducts = warehouse.getProductsByCategory(Category.TESTCATEGORY);
            assertEquals(0, allProducts.size());
        }

        @Test
        void getProductsAfterDate() {
            List<ProductRecord> recentProducts = warehouse.getProductsAfterDate(LocalDate.now().plusDays(5));

            assertEquals(1, recentProducts.size());
            assertEquals("Laptop", recentProducts.get(0).name());
        }

        @Test
        void getProductsAfterDateWhenThereAreNoMatches() {
            List<ProductRecord> recentProducts = warehouse.getProductsAfterDate(LocalDate.now().plusDays(15));
            assertEquals(0, recentProducts.size());
        }

        @Test
        void getProductsMappedByFirstLetter() {
            Map<String, Long> result = warehouse.getProductsMappedByFirstLetter();
            int expected = 2;

            assertEquals(expected, result.get("P"));
        }

        @Test
        void getModifiedProducts() {
            List<ProductRecord> expected = warehouse.getModifiedProducts();
            assertEquals(1, expected.size());
        }

        @Test
        void getCategoriesWithProducts() {
            List<Category> categories = warehouse.getCategoriesWithProducts();
            assertEquals(4, categories.size());
            assertTrue(categories.contains(Category.BOOKS));
            assertFalse(categories.contains(Category.TESTCATEGORY));
        }

        @Test
        void getProductsForCategory() {
            List<String> result = warehouse.getProductsForCategory(Category.BOOKS);
            assertEquals("Tome", result.get(1));
        }

        @Test
        void getAmountOfProductsForCategory() {
            int bookCategoryResult = warehouse.getAmountOfProductsForCategory(Category.BOOKS);
            int clothingCategoryResult = warehouse.getAmountOfProductsForCategory(Category.CLOTHING);
            int testCategoryResult = warehouse.getAmountOfProductsForCategory(Category.TESTCATEGORY);

            assertEquals(bookCategoryResult, 2);
            assertEquals(clothingCategoryResult, 1);
            assertEquals(testCategoryResult, 0);
        }

        @Test
        void getProductsBeginningWithLetter() {
            var result = warehouse.getProductsMappedByFirstLetter();
            assertTrue(result.containsKey("P") && result.get("P") == 2);
            assertTrue(result.containsKey("C") && result.get("C") == 1);
        }

        @Test
        void getProductsWithMaxRating() {
            var result = warehouse.getProductsWithMaxRating();
            assertEquals(2, result.size());
            assertEquals("Pants", result.get(0).name());
        }
    }
}