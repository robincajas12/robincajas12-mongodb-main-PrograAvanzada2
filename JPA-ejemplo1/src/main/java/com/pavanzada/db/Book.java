package com.pavanzada.db;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String isbn;
    private String title;
    private String author;
    private int value;
    
    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_isbn"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @ToString.Exclude
    private List<Author> authors;
}