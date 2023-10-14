package org.example.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.core.MediaType;
import org.example.JacksonContextResolver;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductRecord;
import org.example.exception.ExceptionMapper;
import org.example.interceptor.LoggingFilter;
import org.example.interceptor.LoggingInterceptor;
import org.example.service.Warehouse;
import org.example.service.WarehouseService;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class ProductResourceTest {

    WarehouseService warehouseService;

    ObjectMapper objectMapper;

    Dispatcher dispatcher;

    Logger logger;

    @BeforeEach
    public void setup() {

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        warehouseService = new Warehouse();

        logger = LoggerFactory.getLogger(ExceptionMapper.class);

        dispatcher = MockDispatcherFactory.createDispatcher();
        var resource = new ProductResource(warehouseService);
        dispatcher.getRegistry().addSingletonResource(resource);

        dispatcher.getProviderFactory().registerProvider(JacksonContextResolver.class);

        dispatcher.getProviderFactory().registerProvider(ExceptionMapper.class);
        dispatcher.getProviderFactory().registerProvider(LoggingInterceptor.class);
        dispatcher.getProviderFactory().registerProvider(LoggingFilter.class);
    }

    @Test
    void getProductsReturnsStatus200() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/product");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
    }

    @Test
    void postProductReturnsProductAndStatusCreated() throws Exception {
        var exampleProduct = new Product("Laptop", 2, Category.BOOKS);

        MockHttpRequest request = MockHttpRequest.post("/product");

        String json = objectMapper.writeValueAsString(exampleProduct);
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);
        String jsonString = response.getContentAsString();

        var result = objectMapper.readValue(jsonString, ProductRecord.class);

        assertEquals("Laptop", result.name());
        assertEquals(201, response.getStatus());
    }

    @Test
    void getProductsByCategoryReturnsOnlyProductsWithCorrectCategory() throws Exception {
        warehouseService.addProduct(new Product("Book", 2, Category.BOOKS).toRecord());
        warehouseService.addProduct(new Product("Book 2", 2, Category.BOOKS).toRecord());
        warehouseService.addProduct(new Product("Book 3", 2, Category.BOOKS).toRecord());
        warehouseService.addProduct(new Product("Laptop", 2, Category.ELECTRONICS).toRecord());

        MockHttpRequest request = MockHttpRequest.get("/product?category=books");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);
        String jsonString = response.getContentAsString();

        var responseList = objectMapper.readValue(jsonString, new TypeReference<ArrayList<ProductRecord>>() {
        });

        boolean result = responseList.stream().allMatch(i -> i.category() == Category.BOOKS);

        assertTrue(result);
        assertEquals(200, response.getStatus());
    }
}