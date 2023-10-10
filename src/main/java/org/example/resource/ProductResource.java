package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.interceptor.Log;
import org.example.entities.Category;
import org.example.entities.ProductRecord;
import org.example.service.WarehouseService;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Log
public class ProductResource {

    private WarehouseService warehouse;

    public ProductResource() {
    }

    @Inject
    public ProductResource(WarehouseService warehouse) {
        this.warehouse = warehouse;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid ProductRecord product) {
        return Response.ok(warehouse.addProduct(product), MediaType.APPLICATION_JSON).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public ProductRecord modifyProduct(@Valid ProductRecord product) { return product; }

    @GET
    public Response getAllProducts(@QueryParam("category") String category) {
/*
        warehouse.addProduct(new ProductRecord("", "Laptop", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        warehouse.addProduct(new ProductRecord("", "Laptop2", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
        warehouse.addProduct(new ProductRecord("", "Laptop3", 2, LocalDate.now(), LocalDate.now(), Category.BOOKS));
*/

        if (category != null) {
            System.out.println(warehouse.getProductsByCategory(Category.valueOf(category.toUpperCase())));
            return Response.ok(warehouse.getProductsByCategory(Category.valueOf(category.toUpperCase())), MediaType.APPLICATION_JSON).status(200).build();
        }


        return Response.ok(warehouse.getAllProducts(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    public ProductRecord getProductById(@PathParam("id") String id) {
        return warehouse.getProductById(id);
    }

/*    @GET
    public List<ProductRecord> getProductsByCategory(@QueryParam("category") Category category) {
        return warehouse.getProductsByCategory(category);
    }*/

}
