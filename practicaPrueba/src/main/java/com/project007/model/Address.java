package com.project007.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor
@Builder
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "street", length = 150)
    private String street;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "zip", length = 20)
    private String zip;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    @ToString.Exclude
    private Employee employee;
}
