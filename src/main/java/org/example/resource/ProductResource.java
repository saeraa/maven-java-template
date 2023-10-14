package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Product;
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
        var rating = product.rating();
        var name = product.name();
        var category = product.category();

        Product newProduct = new Product(name, rating, category);

        return Response.status(201).entity(warehouse.addProduct(newProduct.toRecord())).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifyProduct(@Valid ProductRecord product) {
        return Response.status(401).entity(warehouse.modifyProduct(product)).build();
    }

    @GET
    public Response getAllProducts(@Valid @QueryParam("category") String category) {

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

}
