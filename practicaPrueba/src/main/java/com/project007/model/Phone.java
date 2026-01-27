package com.project007.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phone")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
@Builder
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number", nullable = false, length = 50)
    private String number;

    @Column(name = "type", length = 50)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @ToString.Exclude
    private Employee employee;
}
