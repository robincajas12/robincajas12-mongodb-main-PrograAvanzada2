package com.pavanzada.db.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
    private Integer version;
}
