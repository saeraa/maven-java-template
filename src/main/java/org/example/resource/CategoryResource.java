package org.example.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/category")
public class CategoryResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "HELLO";
    }
}
