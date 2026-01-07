package com.progavanzada.models;

import lombok.Data;
import lombok.ToString;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;

@Data
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @SequenceGenerator(
        name = "seqcustomer",
        sequenceName = "seq_customer",
        allocationSize = 1000,
        initialValue = 10000
    )
    @GeneratedValue(generator = "seqcustomer")
    private Long id;
    private String name;
    private String email;
    private Integer version;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PurchaseOrder> purchaseOrders;
}
