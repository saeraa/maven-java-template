package org.example;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.entities.Category;
import org.example.entities.ProductRecord;
import org.example.service.Warehouse;

import java.time.LocalDate;

@ApplicationPath("/api")
public class App extends Application {

}
