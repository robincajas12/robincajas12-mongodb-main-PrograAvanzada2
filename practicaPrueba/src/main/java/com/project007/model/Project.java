package com.project007.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "projects")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Employee> employees = new ArrayList<>();

}
