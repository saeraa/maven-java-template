package org.example.exception;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Set;

@Provider
public class ConstraintViolationExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @Override
    public Response toResponse(final ConstraintViolationException exception) {

        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        final var jsonObject = Json
                .createObjectBuilder()
                .add("host",uriInfo.getAbsolutePath().getHost())
                .add("resource", uriInfo.getAbsolutePath().getPath())
                .add("title", "Validation Errors");


        final var jsonArray = Json.createArrayBuilder();

        for (final var constraint : constraintViolations) {

            String message = constraint.getMessage();
            String propertyPath = constraint.getPropertyPath().toString().split("\\.")[2];

            JsonObject jsonError = Json.createObjectBuilder()
                    .add("field", propertyPath)
                    .add("violationMessage", message)
                    .build();
            jsonArray.add(jsonError);

        }

        JsonObject errorJsonEntity = jsonObject.add("errors", jsonArray.build()).build();
        logger.error("ERROR: " + errorJsonEntity.getJsonArray("errors"));
        return Response.status(Response.Status.BAD_REQUEST).entity(errorJsonEntity).build();
    }
}
