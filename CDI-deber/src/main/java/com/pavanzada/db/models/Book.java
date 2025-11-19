package com.pavanzada.db.models;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
public class Book {
    private String isbn;
    private String title;
    private Double price;
    private Integer version;
}
