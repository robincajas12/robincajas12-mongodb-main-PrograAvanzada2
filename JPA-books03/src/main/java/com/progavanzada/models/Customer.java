package com.progavanzada.models;

import lombok.*;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "customer", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "name"})
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer version;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PurchaseOrder> purchaseOrders;
}
