package com.progavanzada.models;

import lombok.Data;
import lombok.ToString;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "purchase_orders")
@ToString
public class PurchaseOrder {


    @Id
    private Long id;
    @Column(name = "placed_on")
    private LocalDateTime placedOn;
    
    @Column(name = "delivered_on")
    private LocalDateTime deliveredOn;

    @Enumerated(EnumType.ORDINAL)
    private Status status;
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public enum Status {
        PENDING, DELIVERED, CANCELLED
    }
    @ToString.Exclude
    @OneToMany(mappedBy = "purchaseOrder")
    List<LineItem> lineItems;
}
