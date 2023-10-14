package org.example.exception;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

    @Context
    UriInfo uriInfo;
    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {

        final var jsonObject = Json
                .createObjectBuilder()
                .add("host",uriInfo.getAbsolutePath().getHost())
                .add("resource", uriInfo.getAbsolutePath().getPath())
                .add("title", "Errors");

        JsonObject errorJsonEntity = jsonObject.add("errors", exception.getMessage()).build();

        logger.error("ERROR: " + errorJsonEntity.get("errors"));
        return Response.status(Response.Status.BAD_REQUEST).entity(errorJsonEntity).build();
    }
}