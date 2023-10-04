package org.example.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.example.entities.Category;
import org.example.entities.ProductRecord;
import org.example.service.Warehouse;

import java.time.LocalDate;

@Path("/category")
public class CategoryResource {

    private Warehouse warehouse;

    @Inject
    public CategoryResource(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public CategoryResource() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("cat") String cat) {
        warehouse.addProduct(new ProductRecord("", "Laptop", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        warehouse.addProduct(new ProductRecord("", "Laptop2", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        warehouse.addProduct(new ProductRecord("", "Laptop3", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        // get all categories
        var result = warehouse.getProductsForCategory(Category.valueOf(cat));
        return result.toString();
    }
}
