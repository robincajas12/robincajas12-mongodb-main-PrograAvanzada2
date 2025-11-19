package com.pavanzada.db.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Inventory {
    private String bookIsbn;
    private Integer sold;
    private Integer supplied;
    private Integer version;
}
