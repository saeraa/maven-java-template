package org.example.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Category;
import org.example.entities.ProductRecord;
import org.example.service.Warehouse;

import java.time.LocalDate;

@Path("/product")
public class ProductResource {

    private Warehouse warehouse;

    public ProductResource() {
    }

    @Inject
    public ProductResource(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        warehouse.addProduct(new ProductRecord("", "Laptop", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        warehouse.addProduct(new ProductRecord("", "Laptop2", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        warehouse.addProduct(new ProductRecord("", "Laptop3", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));

        return Response.ok(warehouse.getAllProducts(), MediaType.APPLICATION_JSON).build();
    }
}
