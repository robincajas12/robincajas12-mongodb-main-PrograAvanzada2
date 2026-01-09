package com.progavanzada.models;

import lombok.*;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TableGenerator;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
