package org.example.entities;

import java.time.LocalDate;

public record ProductRecord(String id, String name, int rating, LocalDate createdBy, LocalDate modifiedBy, Category category) {
}
