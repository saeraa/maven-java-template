package org.example.interceptor;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class LoggingFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        System.out.println(crc.getMethod() + " " + crc.getUriInfo().getAbsolutePath());
        for (String key : crc.getHeaders().keySet()) {
            System.out.println("[REST Logging] " +key + ": " + crc.getHeaders().get(key));
        }
    }
}
