package com.progavanzada.models;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    private String name;
    private Integer version;
    
    @ManyToMany(mappedBy = "authors")
    @ToString.Exclude
    private List<Book> books;
}