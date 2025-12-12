package com.progavanzada.models;

import lombok.Data;
import lombok.ToString;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Data
@Builder
@Entity
public class LineItem {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;
    
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private List<Book> books;
    private Integer quantity;
}
