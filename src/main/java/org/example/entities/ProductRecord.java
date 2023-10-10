package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ProductRecord(String id,
                            @NotEmpty(message = "Product name must not be empty")
                            @Pattern(regexp = "[a-z-A-Z]*", message = "Product name has invalid characters")
                            String name,
                            @Min(value=1, message = "Minimum rating is 1") @Max(value = 10, message = "Maximum rating is 10") int rating,
                            @JsonIgnore @PastOrPresent(message = "CreatedBy date must be past or present") LocalDate createdBy,
                            @JsonIgnore @FutureOrPresent(message = "ModifiedBy date must be future or present") LocalDate modifiedBy,
                            Category category) {
}
