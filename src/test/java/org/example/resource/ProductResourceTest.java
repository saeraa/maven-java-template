package org.example.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.ws.rs.core.MediaType;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductRecord;
import org.example.service.Warehouse;
import org.example.service.WarehouseService;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    WarehouseService warehouseService;

    Dispatcher dispatcher;

    @BeforeEach
    public void setup() {

        List<ProductRecord> productList = new ArrayList<>();
        productList.add(new ProductRecord("", "Laptop", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        productList.add(new ProductRecord("", "Laptop2", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        productList.add(new ProductRecord("", "Laptop3", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        Mockito.lenient().when(warehouseService.getAllProducts()).thenReturn(productList);

        dispatcher = MockDispatcherFactory.createDispatcher();
        var resource = new ProductResource(warehouseService);
        dispatcher.getRegistry().addSingletonResource(resource);


        // dispatcher.getproviderfactory().registerproviderinstance(mapper)
    }

    @Test
    void getProducts() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/product");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);
        System.out.println(response.getContentAsString());
        assertEquals(200, response.getStatus());
    }

    @Test
    void postProduct() throws Exception {
        ProductRecord exampleProduct = new ProductRecord("", "Laptop", 2, null, null, Category.BOOKS);
        Mockito.when(warehouseService.addProduct(exampleProduct)).thenReturn(exampleProduct);

        MockHttpRequest request = MockHttpRequest.post("/product");
        String json = new ObjectMapper().writeValueAsString(exampleProduct);
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRecord productResponse = objectMapper.readValue(response.getContentAsString(), ProductRecord.class);
        assertEquals("Laptop", productResponse.name());
        assertEquals(200, response.getStatus());

    }


    @Test
    void getProductsByCategory() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/product?category=books");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
    }
}