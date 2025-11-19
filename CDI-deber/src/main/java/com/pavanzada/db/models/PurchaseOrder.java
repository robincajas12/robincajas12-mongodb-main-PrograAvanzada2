package com.pavanzada.db.models;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class PurchaseOrder {
    private Long id;
    private LocalDateTime placedOn;
    private LocalDateTime deliveredOn;
    private Integer status;
    private Integer total;
    private Long customerId;
}
