package com.pavanzada.db;

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
    private Long id;
    private String name;
    private Integer version;
    
    @ManyToMany(mappedBy = "authors")
    @ToString.Exclude
    private List<Book> books;
}