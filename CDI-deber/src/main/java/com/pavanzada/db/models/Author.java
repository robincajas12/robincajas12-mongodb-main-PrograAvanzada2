package com.pavanzada.db.models;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Author {
    private Long id;
    private String name;
    private Integer version;
}
