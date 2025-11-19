package com.pavanzada.db.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class LineItem {
    private Long orderId;
    private Integer idx;
    private String bookIsbn;
    private Integer quantity;
}
